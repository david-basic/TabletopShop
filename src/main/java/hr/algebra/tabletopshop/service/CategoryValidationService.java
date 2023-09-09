package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.dto.CreateCategoryFormDto;
import hr.algebra.tabletopshop.model.items.Category;

import java.util.List;

public interface CategoryValidationService {
    String validateDuplicateCategory(CreateCategoryFormDto categoryToValidate, List<Category> categories);
}
