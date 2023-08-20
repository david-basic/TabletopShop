package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.dto.CreateItemFormDto;

public interface ItemService {

    void deleteItem(String id);
    void createItem(CreateItemFormDto item);

}
