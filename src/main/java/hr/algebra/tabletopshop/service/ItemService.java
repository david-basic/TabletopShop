package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.dto.CreateItemFormDto;
import hr.algebra.tabletopshop.dto.UpdateItemFormDto;
import hr.algebra.tabletopshop.model.items.Item;

public interface ItemService {
    void createItem(CreateItemFormDto item);
    void updateItem(UpdateItemFormDto item);
    void deleteItem(String id);
    Item getItemById(String id);
}
