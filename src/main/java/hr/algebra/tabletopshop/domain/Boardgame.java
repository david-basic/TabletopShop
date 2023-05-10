package hr.algebra.tabletopshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Boardgame {
    private Integer id;
    private String name;
    private Integer year;
    private Double rating;
    private Double weight;
    private Integer playingTime;
    private BigDecimal price;
}
