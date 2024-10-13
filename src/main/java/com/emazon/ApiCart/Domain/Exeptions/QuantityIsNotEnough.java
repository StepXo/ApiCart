package com.emazon.ApiCart.Domain.Exeptions;

public class QuantityIsNotEnough extends RuntimeException{
    public QuantityIsNotEnough(String message) {
        super(message);
    }

}
