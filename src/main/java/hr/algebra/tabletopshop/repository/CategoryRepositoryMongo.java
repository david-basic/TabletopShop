package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.model.items.Category;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryMongo extends MongoRepository<Category, String> {
    @Override
    @NotNull
    List<Category> findAll();
    
    @Override
    @NotNull
    Optional<Category> findById(@NotNull String id);
    
    @Override
    long count();
}
