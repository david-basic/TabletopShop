package hr.algebra.tabletopshop.dto;

import hr.algebra.tabletopshop.domain.Boardgame;

import java.math.BigDecimal;

public class BoardgameDto {
    private Integer id;
    private String name;
    private Integer year;
    private Double rating;
    private Double weight;
    private Integer playingTime;
    private BigDecimal price;

    public BoardgameDto(Boardgame boardgame) {
        this.id = boardgame.getId();
        this.name = boardgame.getName();
        this.year = boardgame.getYear();
        this.rating = boardgame.getRating();
        this.weight = boardgame.getWeight();
        this.playingTime = boardgame.getPlayingTime();
        this.price = boardgame.getPrice();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public Double getRating() {
        return rating;
    }

    public Double getWeight() {
        return weight;
    }

    public Integer getPlayingTime() {
        return playingTime;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
