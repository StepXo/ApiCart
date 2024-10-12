package com.emazon.ApiCart.Domain.Model;

import java.util.List;

public class Item {
    private long id;
    private String name;
    private long quantity;
    private double price;
    private List<Category> category;
    private Brand brand;

    public Item(long id, String name, long quantity, double price, List<Category> category, Brand brand) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }

    public Item() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public List<Category> getCategory() {
        return category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
