package hr.algebra.tabletopshop.domain.boardgames;

import hr.algebra.tabletopshop.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Boardgame implements Serializable {
    private Integer id;
    private String name;
    private Integer published;
    private Double rating;
    private Double weight;
    private Integer duration;
    private BigDecimal price;
    
    public Boardgame(String name, Integer published, Double rating, Double weight, Integer duration, BigDecimal price) {
        this.name = name;
        this.published = published;
        this.rating = rating;
        this.weight = weight;
        this.duration = duration;
        this.price = price;
    }
}
