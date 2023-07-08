package hr.algebra.tabletopshop.domain.minies;

import hr.algebra.tabletopshop.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Miniature extends Product {
    String name;
    MiniatureCategory miniatureCategory;
}
