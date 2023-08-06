package hr.algebra.tabletopshop.domain.items;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Item implements Serializable {
    
    @EqualsAndHashCode.Exclude
    private Integer id;
    @NotNull(message = "Name must exist!")
    @Size(min = 1, max = 30, message = "Name must have more than 1 and less then 30 characters!")
    private String name;
    @NotNull(message = "Category must be assigned!")
    private Category category;
    @NotNull(message = "Price must exist!")
    @PositiveOrZero(message = "Price must be positive or zero!")
    @DecimalMax(value = "999.99", message = "Price must be less then 999.99€!")
    private Double price;
    
}
