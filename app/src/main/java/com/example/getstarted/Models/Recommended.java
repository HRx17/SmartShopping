package com.example.getstarted.Models;

public class Recommended {
    String name;
    String img_url;
    String description;
    String rating;
    String type;
    String discount;


    public Recommended() {
    }

    public Recommended(String name, String img_url, String description, String rating, String type, String discount) {
        this.name = name;
        this.img_url = img_url;
        this.description = description;
        this.rating = rating;
        this.type = type;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
