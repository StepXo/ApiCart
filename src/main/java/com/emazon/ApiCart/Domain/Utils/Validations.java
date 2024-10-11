package com.emazon.ApiCart.Domain.Utils;

import com.emazon.ApiCart.Domain.Exeptions.*;
import com.emazon.ApiCart.Domain.Model.Cart;

public class Validations {
    private static void validateItem(long id) {
        if ( id == 0) {
            throw new InvalidItemException();
        }
    }

    private static void validateQuantity(long quantity) {
        if (quantity < 1) {
            throw new InvalidQuantityException();
        }
    }

    private static void validateUser(String user) {
        if (user == null || user.trim().isEmpty()) {
            throw new InvalidUserException();
        }
    }
    private static void validateCart(Cart cart) {
        if (cart == null) {
            throw new CartIsNullExeption();
        }
        if (cart.getItem().isEmpty() || cart.getQuantity().isEmpty()) {
            throw new ItemNotFoundException();
        }
    }

    public static void validate(Cart cart, String user){
        validateCart(cart);
        validateItem(cart.getItem().get(0));
        validateQuantity(cart.getQuantity().get(0));
        validateUser(user);
    }

    public static void validate(long id, String user){
        validateItem(id);
        validateUser(user);
    }
}
