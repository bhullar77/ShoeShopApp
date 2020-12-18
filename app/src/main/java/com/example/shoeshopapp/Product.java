package com.example.shoeshopapp;

public class Product {
    private int id;
    private String article;
    private String description;
    private String image;
    private int price;
    private String category;

    public Product() {
    }

    public Product(int id, String article, String description, String image, int price, String category) {
        this.id = id;
        this.article = article;
        this.description = description;
        this.image = image;
        this.price = price;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
