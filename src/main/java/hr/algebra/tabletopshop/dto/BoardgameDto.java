package hr.algebra.tabletopshop.dto;

import java.math.BigDecimal;

public record BoardgameDto(Integer Id, String name, Integer year, Double rating, Double weight, Integer playingTime,
                           Double price) {
}
