package com.example.shoeshopapp;

public class Cart {
    private int pid;
    private String article;
    private String description;
    private String image;
    private int qty;
    private int price;
    private int total;

    public Cart() {
    }

    public Cart(int pid, String article, String description, int qty, int price, int total) {
        this.pid = pid;
        this.article = article;
        this.description = description;
        this.qty = qty;
        this.price = price;
        this.total = total;
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
