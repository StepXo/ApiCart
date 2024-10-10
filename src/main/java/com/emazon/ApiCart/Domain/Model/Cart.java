package com.emazon.ApiCart.Domain.Model;

import java.util.List;

public class Cart {
    private long id;
    private List<Item> itemId;
    private long userId;
    private int quantity;
    private String actualizationDate;
    private String creationDate;

    public Cart(long id, List<Item> itemId, long userId, int quantity, String actualizationDate, String creationDate) {
        this.id = id;
        this.itemId = itemId;
        this.userId = userId;
        this.quantity = quantity;
        this.actualizationDate = actualizationDate;
        this.creationDate = creationDate;
    }

    public Cart() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItemId(List<Item> itemId) {
        this.itemId = itemId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setActualizationDate(String actualizationDate) {
        this.actualizationDate = actualizationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public List<Item> getItemId() {
        return itemId;
    }

    public long getUserId() {
        return userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getActualizationDate() {
        return actualizationDate;
    }

    public String getCreationDate() {
        return creationDate;
    }
}
