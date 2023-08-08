package hr.algebra.tabletopshop.repository.mongodb;

import hr.algebra.tabletopshop.domain.items.Item;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepositoryMongo extends MongoRepository<Item, Integer> {
    
    @Query("{name:'?0'}")
    List<Item> findAllByName(String name);
    
    @Query("{category:'?0'}")
    List<Item> findAllByCategory(String category);
    
    Item findTopByOrderByIdDesc();
    
    @Override
    @NotNull
    Optional<Item> findById(@NotNull Integer id);
    
    @Override
    @NotNull
    List<Item> findAll();
  
    @Override
    long count();
}
