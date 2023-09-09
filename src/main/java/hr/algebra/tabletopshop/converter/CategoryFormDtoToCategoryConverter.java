package hr.algebra.tabletopshop.converter;

import hr.algebra.tabletopshop.dto.CreateCategoryFormDto;
import hr.algebra.tabletopshop.model.items.Category;
import hr.algebra.tabletopshop.service.UtilitiesService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryFormDtoToCategoryConverter implements Converter<CreateCategoryFormDto, Category> {
    private final UtilitiesService utilitiesService;
    
    @Override
    public Category convert(@NotNull CreateCategoryFormDto source) {
        return new Category(
                utilitiesService.calculateNextCategoryIdInSequence(),
                source.getName()
        );
    }
}
