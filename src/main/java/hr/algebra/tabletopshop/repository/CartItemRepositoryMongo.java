package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.model.cart.Cart;
import hr.algebra.tabletopshop.model.cart.CartItem;
import hr.algebra.tabletopshop.model.items.Item;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepositoryMongo extends MongoRepository<CartItem, String> {
    
    Optional<CartItem> findByCartAndItem(Cart cart, Item item);
    
    @Aggregation(pipeline = {
            "{$match: {_id: ?0}}",
            "{$unwind: '$cartItems'}",
            "{$group: {_id: '$_id', total: {$sum: '$cartItems.price'}}}"
    })
        // total nije atribut na cart objektu jer ne treba biti, total je samo alias za sumu koja ce se vratiti
    Double calculateTotalPriceForCart(String id);
    
    List<CartItem> findAllByCart(Cart cart);
    
    @Override
    @NotNull
    List<CartItem> findAll();
    
    @Override
    long count();
}
