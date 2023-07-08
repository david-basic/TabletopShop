package hr.algebra.tabletopshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    Integer id;
    ProductCategory productCategory;
    Integer quantity;
    BigDecimal price;
    String description;
    Boolean inCart;
}
