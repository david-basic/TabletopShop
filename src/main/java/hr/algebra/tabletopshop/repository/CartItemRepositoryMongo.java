package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.model.cart.CartItem;
import hr.algebra.tabletopshop.model.items.Item;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepositoryMongo extends MongoRepository<CartItem, Integer> {
    
    Boolean existsByCartItemId(Integer id);
    
    Optional<CartItem> findByCart_CartIdAndItem(@NotNull Integer cartId, Item item);
    
    @Override
    @NotNull
    List<CartItem> findAll();
    
    @Override
    long count();
}
