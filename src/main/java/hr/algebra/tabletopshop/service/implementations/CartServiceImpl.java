package hr.algebra.tabletopshop.service.implementations;

import hr.algebra.tabletopshop.dto.ChangeItemQuantityDto;
import hr.algebra.tabletopshop.dto.RemoveItemFromCartDto;
import hr.algebra.tabletopshop.exceptions.DbEntityNotFoundException;
import hr.algebra.tabletopshop.model.cart.Cart;
import hr.algebra.tabletopshop.model.cart.CartItem;
import hr.algebra.tabletopshop.model.items.Item;
import hr.algebra.tabletopshop.model.users.User;
import hr.algebra.tabletopshop.repository.CartItemRepositoryMongo;
import hr.algebra.tabletopshop.repository.CartRepositoryMongo;
import hr.algebra.tabletopshop.service.CartService;
import hr.algebra.tabletopshop.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    
    private final CartRepositoryMongo cartRepositoryMongo;
    private final CartItemRepositoryMongo cartItemRepositoryMongo;
    private final CurrentUserService currentUserService;
    private final MongoTemplate mongoTemplate;
    private final Cart cart;
    
    @Override
    public Cart getCurrentUserCart() {
        return getCartByUser(currentUserService.getCurrentUser());
    }
    
    @Override
    public void saveCart(Cart cartToSave) {
        List<CartItem> cartItemsToSave = new ArrayList<>(cartToSave.getCartItems());
        cartToSave.getCartItems().clear();
        
        cartRepositoryMongo.findByUser(cartToSave.getUser()).ifPresentOrElse(cart -> {
            List<CartItem> oldCartItems = cartItemRepositoryMongo.findAll();
            List<CartItem> newCartItems = cartItemsToSave.stream().peek(it -> it.setCart(cart)).toList();
            
            cartItemRepositoryMongo.saveAll(oldCartItems.stream().filter(newCartItems::contains).peek(it -> it.setQuantity(newCartItems.stream().filter(in -> in.getItem().equals(it.getItem())).findFirst().get().getQuantity())).toList());
            cartItemRepositoryMongo.saveAll(newCartItems.stream().filter(it -> !oldCartItems.contains(it)).toList());
            
            Double totalPrice = Optional.ofNullable(cartItemRepositoryMongo.calculateTotalPriceForCart(cart.getId())).orElse(0.0);
            cart.setTotalPrice(totalPrice);
            cartRepositoryMongo.save(cart);
        }, () -> {
            Cart newCart = cartRepositoryMongo.save(Cart.copyCart(cartToSave));
            cartItemRepositoryMongo.saveAll(cartItemsToSave.stream().peek(it -> it.setCart(newCart)).toList());
        });
        
    }
    
    @Override
    public void cleanCart() {
        Cart cart = getCurrentUserCart();
        cartItemRepositoryMongo.deleteAll(cart.getCartItems());
        cart.setTotalPrice(0.0);
        cartRepositoryMongo.save(cart);
    }
    
    @Override
    public Cart getCartByUser(User user) {
        if (cartRepositoryMongo.findByUser(user).isPresent()) {
            return cartRepositoryMongo.findByUser(user).orElseThrow(DbEntityNotFoundException::new);
        } else {
            Cart newCart = Cart.copyCart(cart);
            newCart.setUser(user);
            newCart.setCartId(calculateNextCartIdInSequence());
            newCart.setCartItems(new HashSet<>());
            cartRepositoryMongo.save(newCart);
            
            for (var ci : cart.getCartItems()) {
                ci.setCart(newCart);
                cartItemRepositoryMongo.save(ci);
            }
            
            newCart.setCartItems(new HashSet<>(cartItemRepositoryMongo.findAllByCart(newCart)));
            
            cartRepositoryMongo.save(newCart);
            return newCart;
        }
    }
    
    @Override
    public Cart getCartById(String id) {
        return cartRepositoryMongo.findById(id).orElseThrow(DbEntityNotFoundException::new);
    }
    
    @Override
    public Cart getCartByItemId(Integer id) {
        return cartRepositoryMongo.findByCartId(id).orElseThrow(DbEntityNotFoundException::new);
    }
    
    @Override
    public void addItemToCart(Item item, Integer quantity) {
        Cart cart = getCurrentUserCart();
        cartItemRepositoryMongo.findByCartAndItem(cart, item).ifPresentOrElse(cartItem -> {
            cartItem.addQuantity(quantity);
            cartItemRepositoryMongo.save(cartItem);
            cart.addItem(cartItem);
            cartRepositoryMongo.save(cart);
        }, () -> {
            CartItem cartItem = new CartItem(calculateNextCartItemIdInSequence(), item, quantity, cart);
            cartItemRepositoryMongo.save(cartItem);
            
            cart.addItem(cartItem);
            cartRepositoryMongo.save(cart);
        });
    }
    
    @Override
    public RemoveItemFromCartDto removeItemFromCart(Item item) {
        Cart cart = getCurrentUserCart();
        
        return cartItemRepositoryMongo.findByCartAndItem(cart, item).map(it -> {
            cart.subtractFromTotal(it.getTotal());
            cartRepositoryMongo.save(cart);
            cartItemRepositoryMongo.delete(it);
            
            return new RemoveItemFromCartDto(cart.getTotalPrice());
        }).orElse(new RemoveItemFromCartDto(0.0));
    }
    
    @Override
    public ChangeItemQuantityDto incrementItemInCart(Item item) {
        Cart cart = getCurrentUserCart();
        
        return cartItemRepositoryMongo.findByCartAndItem(cart, item).map(it -> {
            it.incrementQuantity();
            cartItemRepositoryMongo.save(it);
            cart.addToTotal(it.getItemPrice());
            cartRepositoryMongo.save(cart);
            
            return new ChangeItemQuantityDto(it.getTotal(), cart.getTotalPrice());
        }).orElse(new ChangeItemQuantityDto(0.0, cart.getTotalPrice()));
    }
    
    @Override
    public ChangeItemQuantityDto decrementItemInCart(Item item) {
        Cart cart = getCurrentUserCart();
        
        return cartItemRepositoryMongo.findByCartAndItem(cart, item).map(it -> {
            it.decrementQuantity();
            Integer quantity = it.getQuantity();
            if (quantity == 0) {
                cartItemRepositoryMongo.delete(it);
            } else {
                cartItemRepositoryMongo.save(it);
            }
            
            cart.subtractFromTotal(it.getItemPrice());
            cartRepositoryMongo.save(cart);
            
            return new ChangeItemQuantityDto(quantity == 0 ? 0.0 : it.getTotal(), cart.getTotalPrice());
        }).orElse(new ChangeItemQuantityDto(0.0, cart.getTotalPrice()));
    }
    
    @Override
    public void deleteCartByUser(User user) {
        if (cartRepositoryMongo.findByUser(user).isPresent()) {
            Cart dbCart = cartRepositoryMongo.findByUser(user).orElseThrow(DbEntityNotFoundException::new);
            cartItemRepositoryMongo.deleteAll(dbCart.getCartItems());
            cartRepositoryMongo.delete(dbCart);
        }
    }
    
    private Integer calculateNextCartItemIdInSequence() {
        Query query = new Query().with(Sort.by(Sort.Order.desc("cartItemId"))).limit(1);
        CartItem lastCartItem = mongoTemplate.findOne(query, CartItem.class);
        if (lastCartItem != null) {
            return lastCartItem.getCartItemId() + 1;
        }
        return 1;
    }
    
    private Integer calculateNextCartIdInSequence() {
        Query query = new Query().with(Sort.by(Sort.Order.desc("cartId"))).limit(1);
        Cart lastCart = mongoTemplate.findOne(query, Cart.class);
        if (lastCart != null) {
            return lastCart.getCartId() + 1;
        }
        return 1;
    }
    
}
