package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.model.items.Category;
import hr.algebra.tabletopshop.model.items.Item;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepositoryMongo extends MongoRepository<Item, String> {
    
    List<Item> findAllByName(String name);
    
    List<Item> findAllByCategory(Category category);
    
    List<Item> findAllByCategoryName(String category_name);
    
    Item findTopByOrderByItemIdDesc();
    
    
    @Override
    @NotNull
    Optional<Item> findById(@NotNull String id);
    
    Optional<Item> findByItemId(Integer itemId);
    
    @Override
    @NotNull
    List<Item> findAll();
    
    @Override
    long count();
}
