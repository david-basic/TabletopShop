package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.dto.CreateCategoryFormDto;
import hr.algebra.tabletopshop.dto.UpdateCategoryFormDto;
import hr.algebra.tabletopshop.model.items.Category;

import java.util.List;

public interface CategoryService {
    void createCategory(CreateCategoryFormDto createCategoryFormDto);
    Category getCategoryById(String id);
    List<Category> getAllCategories();
    void deleteCategory(String id);
    void updateCategory(UpdateCategoryFormDto category);
}
