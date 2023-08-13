package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.domain.items.Category;
import hr.algebra.tabletopshop.domain.items.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Deprecated
@Repository
public class ItemRepositoryMock implements ItemRepository {
    
    private List<Item> itemsList;
    
    public ItemRepositoryMock() {
        itemsList = new ArrayList<>();
        
        Item item1 = new Item(1, "Agricola", Category.BOARDGAME, "Great game!", 20, 50.00);
        Item item2 = new Item(2, "7 Wonders", Category.BOARDGAME, "Complete novelty on the market!", 30,  60.00);
        Item item3 = new Item(3, "Ankh", Category.BOARDGAME, "Best old Egypt game out there!", 5, 250.00);
        
        itemsList.add(item1);
        itemsList.add(item2);
        itemsList.add(item3);
    }
    
    @Override
    public List<Item> getAllItems() {
        return itemsList;
    }
    
    @Override
    public void saveNewItem(Item item) {
        itemsList.add(item);
    }
    
    @Override
    public List<Item> filterItems(Item filter) {
        List<Item> filteredList = getAllItems();
        
        if (Optional.ofNullable(filter.getId()).isPresent()) {
            filteredList = filteredList.stream().filter(item -> item.getId().equals(filter.getId())).collect(Collectors.toList());
        }
        if (Optional.ofNullable(filter.getName()).isPresent()) {
            filteredList = filteredList.stream().filter(item -> item.getName().equals(filter.getName())).collect(Collectors.toList());
        }
        if (Optional.ofNullable(filter.getCategory()).isPresent()) {
            filteredList = filteredList.stream().filter(item -> item.getCategory().equals(filter.getCategory())).collect(Collectors.toList());
        }
        if (Optional.ofNullable(filter.getDescription()).isPresent()) {
            filteredList = filteredList.stream().filter(item -> item.getDescription().equals(filter.getDescription())).collect(Collectors.toList());
        }
        if (Optional.ofNullable(filter.getQuantity()).isPresent()) {
            filteredList = filteredList.stream().filter(item -> item.getQuantity().equals(filter.getQuantity())).collect(Collectors.toList());
        }
        if (Optional.ofNullable(filter.getPrice()).isPresent()) {
            filteredList = filteredList.stream().filter(item -> item.getPrice().equals(filter.getPrice())).collect(Collectors.toList());
        }
        
        return filteredList;
    }
}
