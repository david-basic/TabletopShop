package hr.algebra.tabletopshop.domain.dice;

import hr.algebra.tabletopshop.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dice extends Product {
    String name;
    DiceCategory diceCategory;
}
