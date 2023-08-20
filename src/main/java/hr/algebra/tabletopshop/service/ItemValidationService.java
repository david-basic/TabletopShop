package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.dto.CreateItemFormDto;
import hr.algebra.tabletopshop.model.items.Item;

import java.util.List;

public interface ItemValidationService {
    String validateDuplicateItem(CreateItemFormDto itemToValidate, List<Item> items);
}
