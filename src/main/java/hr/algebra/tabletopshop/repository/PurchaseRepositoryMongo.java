package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.model.purchase.Purchase;
import hr.algebra.tabletopshop.model.users.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PurchaseRepositoryMongo extends MongoRepository<Purchase, String> {
    @Override
    @NotNull
    List<Purchase> findAll();
    
    @NotNull Optional<Purchase> findById(@NotNull String id);
    
    List<Purchase> findAllByCreatedAtBetween(Date startDate, Date endDate);
    
    List<Purchase> findAllByUser(User user);
    
    Optional<Purchase>findByPaypalId(String paypalId);
}
