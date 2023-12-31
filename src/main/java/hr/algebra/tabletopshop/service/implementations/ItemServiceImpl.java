package hr.algebra.tabletopshop.service.implementations;

import hr.algebra.tabletopshop.dto.CreateItemFormDto;
import hr.algebra.tabletopshop.dto.UpdateItemFormDto;
import hr.algebra.tabletopshop.exceptions.DbEntityNotFoundException;
import hr.algebra.tabletopshop.model.items.Item;
import hr.algebra.tabletopshop.repository.ItemRepositoryMongo;
import hr.algebra.tabletopshop.service.CategoryService;
import hr.algebra.tabletopshop.service.ItemService;
import hr.algebra.tabletopshop.service.UtilitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    
    private final ItemRepositoryMongo itemRepositoryMongo;
    private final MongoTemplate mongoTemplate;
    private final UtilitiesService utilitiesService;
    private final CategoryService categoryService;
    
    @Override
    public void deleteItem(String id) {
        Item deletedItem = mongoTemplate.findById(id, Item.class);
        if (deletedItem != null) {
            Integer deletedItem_ItemId = deletedItem.getItemId();
            itemRepositoryMongo.delete(deletedItem);
            
            Query query = new Query(Criteria.where("itemId").gt(deletedItem_ItemId));
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
        itemRepositoryMongo.save(new Item(newItemId, formItemDtoToItem.getName(), categoryService.getCategoryById(formItemDtoToItem.getIdCategory()), formItemDtoToItem.getDescription(), formItemDtoToItem.getQuantity(), formItemDtoToItem.getPrice()));
    }
    
    @Override
    public void updateItem(UpdateItemFormDto updateItemFormDto) {
        Item itemToUpdate = itemRepositoryMongo.findById(updateItemFormDto.getId()).orElseThrow(DbEntityNotFoundException::new);
        itemToUpdate.setName(updateItemFormDto.getName());
        itemToUpdate.setCategory(categoryService.getCategoryById(updateItemFormDto.getIdCategory()));
        itemToUpdate.setDescription(updateItemFormDto.getDescription());
        itemToUpdate.setQuantity(updateItemFormDto.getQuantity());
        itemToUpdate.setPrice(updateItemFormDto.getPrice());
        
        itemRepositoryMongo.save(itemToUpdate);
    }
    
    @Override
    public Item getItemById(String id) {
        return itemRepositoryMongo.findById(id).orElseThrow(DbEntityNotFoundException.supplier());
    }
}
