package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.dto.CreateItemFormDto;
import hr.algebra.tabletopshop.model.items.Item;

public interface ItemService {

    void deleteItem(String id);
    void createItem(CreateItemFormDto item);
    
    Item getItemById(String id);

}
