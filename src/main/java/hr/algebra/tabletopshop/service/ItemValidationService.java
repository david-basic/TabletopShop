package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.domain.items.Item;

import java.util.List;

public interface ItemValidationService {
    String validateDuplicateItem(Item itemToValidate, List<Item> items);
}
