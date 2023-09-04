package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.model.purchase.PurchaseItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseItemRepositoryMongo extends MongoRepository<PurchaseItem, String> {
}
