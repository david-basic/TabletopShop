package hr.algebra.tabletopshop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCategoryFormDto {
    @NotNull(message = "Category name must exist!")
    @Size(min = 1, max = 50, message = "Category name must be between 1 and 50 characters long!")
    private String name;
}
