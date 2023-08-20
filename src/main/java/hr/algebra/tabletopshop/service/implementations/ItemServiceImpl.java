package hr.algebra.tabletopshop.service.implementations;

import hr.algebra.tabletopshop.dto.CreateItemFormDto;
import hr.algebra.tabletopshop.model.items.Item;
import hr.algebra.tabletopshop.repository.ItemRepositoryMongo;
import hr.algebra.tabletopshop.service.ItemService;
import hr.algebra.tabletopshop.service.UtilitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    
    private final ItemRepositoryMongo itemRepositoryMongo;
    private final MongoTemplate mongoTemplate;
    private final UtilitiesService utilitiesService;
    
    @Override
    public void deleteItem(Integer itemId) {
        Item deletedItem = mongoTemplate.findById(itemId, Item.class);
        if (deletedItem != null) {
            Integer deletedId = deletedItem.getItemId();
            itemRepositoryMongo.delete(deletedItem);
            
            Query query = new Query(Criteria.where("itemId").gt(deletedId));
            List<Item> itemsToUpdateIds = mongoTemplate.find(query, Item.class);
            
            for (Item item : itemsToUpdateIds) {
                item.setItemId(item.getItemId() - 1);
                itemRepositoryMongo.save(item);
            }
        }
    }
    
    @Override
    public void createItem(CreateItemFormDto formItemDtoToItem) {
        Integer newItemId = utilitiesService.calculateNextItemIdInSequence();
        itemRepositoryMongo.save(new Item(newItemId, formItemDtoToItem.getName(), formItemDtoToItem.getCategory(), formItemDtoToItem.getDescription(), formItemDtoToItem.getQuantity(), formItemDtoToItem.getPrice()));
    }
}
