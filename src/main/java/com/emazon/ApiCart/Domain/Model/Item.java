package com.emazon.ApiCart.Domain.Model;

import java.util.List;

public class Item {
    private long id;
    private long quantity;
    private double price;


    public Item(long id, long quantity, double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public Item() {
    }

    public long getId() {
        return id;
    }

    public long getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
