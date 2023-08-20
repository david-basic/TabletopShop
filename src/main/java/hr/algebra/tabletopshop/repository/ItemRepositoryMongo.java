package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.model.items.Item;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepositoryMongo extends MongoRepository<Item, String> {
    
    @Query("{name:'?0'}")
    List<Item> findAllByName(String name);
    
    @Query("{category:'?0'}")
    List<Item> findAllByCategory(String category);
    
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
