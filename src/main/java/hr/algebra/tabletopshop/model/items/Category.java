package hr.algebra.tabletopshop.model.items;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document("categories")
@ToString(doNotUseGetters = true)
public class Category implements Serializable {
    @Id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String id;
    
    @NotNull(message = "Category id must exist!")
    @Positive(message = "Category id must be a positive number!")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Integer categoryId;
    
    @NotNull(message = "Category name must exist!")
    @Size(min = 1, max = 50, message = "Category name must be between 1 and 50 characters long!")
    private String name;
    
    @Builder
    public Category(@NotNull Integer categoryId, @NotNull String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
}
