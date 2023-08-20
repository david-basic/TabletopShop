package hr.algebra.tabletopshop.service.implementations;

import hr.algebra.tabletopshop.model.items.Item;
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
            
            System.out.println("new Item Id: " + lastItem.getItemId() + 1);
            return lastItem.getItemId() + 1;
        }
        System.out.println("new Item Id: 1");
        return 1;
    }
}