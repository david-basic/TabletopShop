package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.dto.CreateItemFormDto;

public interface ItemService {

    void deleteItem(Integer itemId);
    void createItem(CreateItemFormDto item);

}
