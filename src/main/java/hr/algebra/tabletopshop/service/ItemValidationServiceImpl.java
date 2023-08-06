package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.domain.items.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemValidationServiceImpl implements ItemValidationService {
    @Override
    public String validateDuplicateItem(Item itemToValidate, List<Item> items) {
        String message = "";
        
        for (Item item : items) {
            if (item.equals(itemToValidate)) {
                message = "Item already exists!";
                break;
            }
        }
        
        return message;
    }
}
