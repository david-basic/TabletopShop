package hr.algebra.tabletopshop.domain.boardgames;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Boardgame implements Serializable {
    
    @EqualsAndHashCode.Exclude
    private Integer id;
    @NotNull(message = "Name must exist!")
    @Size(min = 1, max = 30, message = "Name must have more than 1 and less then 30 characters!")
    private String name;
    @NotNull(message = "Published year must exist!")
    @Positive(message = "Published year must be a positive number!")
    @Range(min = 1950, max = 2023, message = "Year must be over 1950 and less then 2023!")
    private Integer published;
    @NotNull(message = "Rating must exist!")
    @PositiveOrZero(message = "Rating must be a positive number or zero!")
    @DecimalMax(value = "10.0", message = "Rating must be a positive number and less or equal 10.0!")
    private Double rating;
    @NotNull(message = "Weight must exist!")
    @PositiveOrZero(message = "Weight must be a positive number or zero!")
    @DecimalMax(value = "5.0", message = "Weight must be less or equal 5.0!")
    private Double weight;
    @NotNull(message = "Playing time must exist!")
    @Positive(message = "Playing time must be a positive number!")
    @Max(value = 480, message = "Game play time can not be longer then 480 minutes!")
    private Integer duration;
    @NotNull(message = "Price must exist!")
    @PositiveOrZero(message = "Price must be positive or zero!")
    @DecimalMax(value = "999.99", message = "Price must be less then 999.99€!")
    private Double price;
    
}
