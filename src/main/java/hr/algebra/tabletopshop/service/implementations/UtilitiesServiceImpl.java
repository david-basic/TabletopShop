package hr.algebra.tabletopshop.service.implementations;

import hr.algebra.tabletopshop.model.cart.Cart;
import hr.algebra.tabletopshop.model.items.Category;
import hr.algebra.tabletopshop.model.items.Item;
import hr.algebra.tabletopshop.model.logging.LoginLog;
import hr.algebra.tabletopshop.service.UtilitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UtilitiesServiceImpl implements UtilitiesService {
    private final MongoTemplate mongoTemplate;
    
    @Override
    public Integer calculateNextItemIdInSequence() {
        Query query = new Query().with(Sort.by(Sort.Order.desc("itemId"))).limit(1);
        Item lastItem = Objects.requireNonNull(mongoTemplate).findOne(query, Item.class);
        if (lastItem != null) {
            return lastItem.getItemId() + 1;
        }
        return 1;
    }
    
    @Override
    public Integer calculateNextCartIdInSequence() {
        Query query = new Query().with(Sort.by(Sort.Order.desc("cartId"))).limit(1);
        Cart lastCart = Objects.requireNonNull(mongoTemplate).findOne(query, Cart.class);
        if (lastCart != null) {
            return lastCart.getCartId() + 1;
        }
        return 1;
    }
    
    @Override
    public Integer calculateNextCategoryIdInSequence() {
        Query query = new Query().with(Sort.by(Sort.Order.desc("categoryId"))).limit(1);
        Category lastCategory = Objects.requireNonNull(mongoTemplate).findOne(query, Category.class);
        if (lastCategory != null) {
            return lastCategory.getCategoryId() + 1;
        }
        return 1;
    }
    
    @Override
    public Integer calculateNextLogIdInSequence() {
        Query query = new Query().with(Sort.by(Sort.Order.desc("logId"))).limit(1);
        LoginLog lastLoginLog = Objects.requireNonNull(mongoTemplate).findOne(query, LoginLog.class);
        if (lastLoginLog != null) {
            return lastLoginLog.getLogId() + 1;
        }
        return 1;
    }
}
