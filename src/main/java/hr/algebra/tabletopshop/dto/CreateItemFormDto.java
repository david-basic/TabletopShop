package hr.algebra.tabletopshop.dto;

import hr.algebra.tabletopshop.model.items.Category;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemFormDto {
    @NotNull(message = "Name must exist!")
    @Size(min = 1, max = 100, message = "Name must have more than 1 and less then 100 characters!")
    private String name;
    
    @NotNull(message = "Category must be assigned!")
    private Category category;
    
    @NotNull(message = "Description must exist!")
    @Size(min = 1, max = 200, message = "Description must have more than 1 and less then 200 characters!")
    private String description;
    
    @NotNull(message = "Quantity has to be given!")
    @PositiveOrZero(message = "Quantity must be positive or zero!")
    private Integer quantity;
    
    @NotNull(message = "Price must exist!")
    @PositiveOrZero(message = "Price must be positive or zero!")
    @DecimalMax(value = "999.99", message = "Price must be less then 999.99€!")
    private Double price;
}
