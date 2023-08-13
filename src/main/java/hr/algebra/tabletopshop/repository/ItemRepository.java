package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.domain.items.Item;

import java.util.List;

@Deprecated
public interface ItemRepository {
    List<Item> getAllItems();
    void saveNewItem(Item item);
    List<Item> filterItems(Item filter);
}
