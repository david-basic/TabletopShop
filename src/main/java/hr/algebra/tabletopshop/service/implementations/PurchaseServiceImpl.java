package hr.algebra.tabletopshop.service.implementations;

import hr.algebra.tabletopshop.dto.CreatePurchaseDto;
import hr.algebra.tabletopshop.dto.PurchaseFormDto;
import hr.algebra.tabletopshop.exceptions.DbEntityNotFoundException;
import hr.algebra.tabletopshop.model.cart.Cart;
import hr.algebra.tabletopshop.model.items.Item;
import hr.algebra.tabletopshop.model.purchase.PaymentMethod;
import hr.algebra.tabletopshop.model.purchase.Purchase;
import hr.algebra.tabletopshop.model.purchase.PurchaseItem;
import hr.algebra.tabletopshop.model.users.User;
import hr.algebra.tabletopshop.payload.paypal.CreatePurchaseResponse;
import hr.algebra.tabletopshop.repository.ItemRepositoryMongo;
import hr.algebra.tabletopshop.repository.PayPalRepository;
import hr.algebra.tabletopshop.repository.PurchaseItemRepositoryMongo;
import hr.algebra.tabletopshop.repository.PurchaseRepositoryMongo;
import hr.algebra.tabletopshop.service.CartService;
import hr.algebra.tabletopshop.service.CurrentUserService;
import hr.algebra.tabletopshop.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepositoryMongo purchaseRepositoryMongo;
    private final PurchaseItemRepositoryMongo purchaseItemRepositoryMongo;
    private final PayPalRepository payPalRepository;
    private final ItemRepositoryMongo itemRepositoryMongo;
    private final CurrentUserService currentUserService;
    private final MongoTemplate mongoTemplate;
    private final CartService cartService;
    
    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepositoryMongo.findAll();
    }
    
    @Override
    public Purchase getPurchaseById(String id) {
        return purchaseRepositoryMongo.findById(id).orElseThrow(DbEntityNotFoundException::new);
    }
    
    @Override
    public Purchase getPurchaseByPayPalId(String paypalId) {
        return purchaseRepositoryMongo.findByPaypalId(paypalId)
                .orElseThrow(DbEntityNotFoundException::new);
    }
    
    @Override
    public List<Purchase> getAllPurchasesByUser(User user) {
        return purchaseRepositoryMongo.findAllByUser(user);
    }
    
    @Override
    public void cancelOrder(String token) {
        Purchase purchase = getPurchaseByPayPalId(token);
        
        List<Item> items = purchase.getPurchaseItems().stream()
                .map(it -> {
                    Item item = it.getItem();
                    item.addQuantityToStock(it.getQuantity());
                    return item;
                })
                .toList();
        itemRepositoryMongo.saveAll(items);
        purchaseItemRepositoryMongo.deleteAll(purchase.getPurchaseItems());
        purchaseRepositoryMongo.delete(purchase);
    }
    
    @Override
    public CreatePurchaseDto createPurchase(PurchaseFormDto purchaseFormDto) {
        User currentUser = currentUserService.getCurrentUser();
        Cart currentUserCart = cartService.getCurrentUserCart();
        
        if (purchaseFormDto.getPaymentMethod().equals(PaymentMethod.PAY_ON_DELIVERY.getDisplayName())) {
            Purchase savedPurchase = purchaseRepositoryMongo.save(Purchase.builder()
                                                                          .user(currentUser)
                                                                          .purchaseId(calculateNextPurchaseIdInSequence())
                                                                          .purchaseTotal(currentUserCart.getTotalPrice())
                                                                          .firstName(purchaseFormDto.getFirstName())
                                                                          .lastName(purchaseFormDto.getLastName())
                                                                          .email(purchaseFormDto.getEmail())
                                                                          .city(purchaseFormDto.getCity())
                                                                          .address1(purchaseFormDto.getAddress1())
                                                                          .address2(purchaseFormDto.getAddress2())
                                                                          .county(purchaseFormDto.getCounty())
                                                                          .zipCode(purchaseFormDto.getZipCode())
                                                                          .paymentMethod(PaymentMethod.PAY_ON_DELIVERY)
                                                                          .createdAt(new Date())
                                                                          .purchaseItems(new HashSet<>())
                                                                          .build());
            
            List<PurchaseItem> savedPurchaseItems = purchaseItemRepositoryMongo.saveAll(currentUserCart.getCartItems().stream().map(item -> PurchaseItem.builder()
                    .purchaseItemId(calculateNextPurchaseItemIdInSequence())
                    .purchase(savedPurchase)
                    .item(item.getItem())
                    .quantity(item.getQuantity())
                    .build()).collect(Collectors.toList())
            );
            
            savedPurchase.setPurchaseItems(new HashSet<>(savedPurchaseItems));
            purchaseRepositoryMongo.save(savedPurchase);
            
            List<Item> itemList = savedPurchaseItems.stream().map(item -> {
                Item it = item.getItem();
                it.removeQuantityFromStock(item.getQuantity());
                return it;
            }).toList();
            itemRepositoryMongo.saveAll(itemList);
            
            return CreatePurchaseDto.builder()
                    .purchase(savedPurchase)
                    .build();
        } else {
            CreatePurchaseResponse createPurchaseResponse = payPalRepository.createOrder(currentUserCart).orElseThrow(DbEntityNotFoundException::new);
            
            Purchase newPurchase = Purchase.builder()
                    .user(currentUser)
                    .purchaseId(calculateNextPurchaseIdInSequence())
                    .paypalId(createPurchaseResponse.getId())
                    .purchaseTotal(currentUserCart.getTotalPrice())
                    .address1(purchaseFormDto.getAddress1())
                    .address2(purchaseFormDto.getAddress2())
                    .paymentMethod(PaymentMethod.PAYPAL)
                    .county(purchaseFormDto.getCounty())
                    .createdAt(new Date())
                    .zipCode(purchaseFormDto.getZipCode()).build();
            purchaseRepositoryMongo.save(newPurchase);
            
            Set<PurchaseItem> newPurchaseItems = currentUserCart.getCartItems().stream().map(it -> PurchaseItem.builder()
                    .purchaseItemId(calculateNextPurchaseItemIdInSequence())
                    .purchase(newPurchase)
                    .item(it.getItem())
                    .quantity(it.getQuantity())
                    .build()
            ).collect(Collectors.toUnmodifiableSet());
            
            List<PurchaseItem> purchaseItems = purchaseItemRepositoryMongo.saveAll(newPurchaseItems);
            newPurchase.setPurchaseItems(new HashSet<>(purchaseItems));
            
            List<Item> items = purchaseItems.stream().map(it -> {
                Item item = it.getItem();
                item.removeQuantityFromStock(it.getQuantity());
                return item;
            }).toList();
            itemRepositoryMongo.saveAll(items);
            
            String redirectLink = createPurchaseResponse.getLinks().stream().filter(link -> link.getRel().equals("approve")).findFirst()
                    .orElseThrow(RuntimeException::new)
                    .getHref();
            
            return CreatePurchaseDto.builder()
                    .purchase(newPurchase)
                    .payPalLink(redirectLink)
                    .build();
        }
    }
    
    private Integer calculateNextPurchaseIdInSequence() {
        Query query = new Query().with(Sort.by(Sort.Order.desc("purchaseId"))).limit(1);
        Purchase lastPurchase = mongoTemplate.findOne(query, Purchase.class);
        if (lastPurchase != null) {
            return lastPurchase.getPurchaseId() + 1;
        }
        return 1;
    }
    
    private Integer calculateNextPurchaseItemIdInSequence() {
        Query query = new Query().with(Sort.by(Sort.Order.desc("purchaseItemId"))).limit(1);
        PurchaseItem lastPurchaseItem = mongoTemplate.findOne(query, PurchaseItem.class);
        if (lastPurchaseItem != null) {
            return lastPurchaseItem.getPurchaseItemId() + 1;
        }
        return 1;
    }
}
