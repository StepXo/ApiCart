package com.emazon.ApiCart.Domain.Model;

import java.util.List;

public class Cart {
    private long id;
    private List<Long> item;
    private long userId;
    private List<Long> quantity;
    private String actualizationDate;
    private String creationDate;

    public Cart(long id, List<Long> item, long userId, List<Long> quantity, String actualizationDate, String creationDate) {
        this.id = id;
        this.item = item;
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

    public void setItem(List<Long> item) {
        this.item = item;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setQuantity(List<Long> quantity) {
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

    public List<Long> getItem() {
        return item;
    }

    public long getUserId() {
        return userId;
    }

    public List<Long> getQuantity() {
        return quantity;
    }

    public String getActualizationDate() {
        return actualizationDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

}
