package hr.algebra.tabletopshop.service.implementations;

import hr.algebra.tabletopshop.converter.CategoryFormDtoToCategoryConverter;
import hr.algebra.tabletopshop.dto.CreateCategoryFormDto;
import hr.algebra.tabletopshop.model.items.Category;
import hr.algebra.tabletopshop.service.CategoryValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryValidationServiceImpl implements CategoryValidationService {
    private CategoryFormDtoToCategoryConverter categoryFormDtoToCategoryConverter;
    
    @Override
    public String validateDuplicateCategory(CreateCategoryFormDto categoryToValidate, List<Category> categories) {
        String message = "";
        
        Category newCategory = categoryFormDtoToCategoryConverter.convert(categoryToValidate);
        
        for (Category category : categories) {
            if (category.equals(newCategory)) {
                message = "Category already exists!";
                break;
            }
        }
        return message;
    }
}
