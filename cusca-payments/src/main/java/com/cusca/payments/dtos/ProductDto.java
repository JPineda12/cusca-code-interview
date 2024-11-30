package com.cusca.payments.dtos;

public class ProductDto {

    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
    private RatingDto Rating;

    public ProductDto(Long id, String title, Double price, String description, String category, String image, RatingDto rating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        Rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public RatingDto getRating() {
        return Rating;
    }

    public void setRating(RatingDto rating) {
        Rating = rating;
    }
}
