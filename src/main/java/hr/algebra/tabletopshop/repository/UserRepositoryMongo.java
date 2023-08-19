package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.model.users.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryMongo extends MongoRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    
    Boolean existsByUsername(String username);
    
    @NotNull Optional<User> findById(@NotNull Integer id);
    
    @Override
    @NotNull
    List<User> findAll();
    
    @Override
    long count();
}
