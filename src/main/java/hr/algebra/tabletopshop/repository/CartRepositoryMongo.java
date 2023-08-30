package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.model.cart.Cart;
import hr.algebra.tabletopshop.model.users.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepositoryMongo extends MongoRepository<Cart, String> {
    
    Optional<Cart> findByUser(User user);
    
    Boolean existsByUser(User user);
    
    @NotNull Optional<Cart> findById(@NotNull String id);
    
    Optional<Cart> findByCartId(Integer cartId);
    
    @Override
    @NotNull
    List<Cart> findAll();
    
    @Override
    long count();
}
