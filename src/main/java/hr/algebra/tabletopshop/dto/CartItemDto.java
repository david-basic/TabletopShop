package hr.algebra.tabletopshop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    
    private String id;
    
    @NotNull(message = "Quantity must exist!")
    @Positive(message = "Quantity can not be negative!")
    private Integer quantity;
    
}
