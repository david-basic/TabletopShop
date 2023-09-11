package hr.algebra.tabletopshop.converter;

import hr.algebra.tabletopshop.dto.CreateItemFormDto;
import hr.algebra.tabletopshop.model.items.Item;
import hr.algebra.tabletopshop.service.CategoryService;
import hr.algebra.tabletopshop.service.UtilitiesService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemFormDtoToItemConverter implements Converter<CreateItemFormDto, Item> {
    private final UtilitiesService utilitiesService;
    private final CategoryService categoryService;
    
    @Override
    public Item convert(@NotNull CreateItemFormDto source) {
        return new Item(
                utilitiesService.calculateNextItemIdInSequence(),
                source.getName(),
                categoryService.getCategoryById(source.getIdCategory()),
                source.getDescription(),
                source.getQuantity(),
                source.getPrice()
        );
    }
}
