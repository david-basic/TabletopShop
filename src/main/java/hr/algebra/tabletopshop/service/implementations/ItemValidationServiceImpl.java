package hr.algebra.tabletopshop.service.implementations;

import hr.algebra.tabletopshop.converter.ItemFormDtoToItemConverter;
import hr.algebra.tabletopshop.dto.CreateItemFormDto;
import hr.algebra.tabletopshop.model.items.Item;
import hr.algebra.tabletopshop.service.ItemValidationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemValidationServiceImpl implements ItemValidationService {
    private ItemFormDtoToItemConverter itemFormDtoToItemConverter;
    
    @Override
    public String validateDuplicateItem(CreateItemFormDto itemToValidate, List<Item> items) {
        String message = "";
        
        Item newItem = itemFormDtoToItemConverter.convert(itemToValidate);
        
        for (Item item : items) {
            if (item.equals(newItem)) {
                message = "Item already exists!";
                break;
            }
        }
        
        return message;
    }
}
