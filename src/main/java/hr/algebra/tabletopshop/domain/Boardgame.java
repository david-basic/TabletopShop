package hr.algebra.tabletopshop.domain;

import java.math.BigDecimal;

public class Boardgame {
    private Integer id;
    private String name;
    private Integer year;
    private Double rating;
    private Double weight;
    private Integer playingTime;
    private BigDecimal price;

    public Boardgame(Integer id, String name, Integer year, Double rating, Double weight, Integer playingTime, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.weight = weight;
        this.playingTime = playingTime;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(Integer playingTime) {
        this.playingTime = playingTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
