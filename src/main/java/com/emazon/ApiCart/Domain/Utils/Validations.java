package com.emazon.ApiCart.Domain.Utils;

import com.emazon.ApiCart.Domain.Exeptions.*;
import com.emazon.ApiCart.Domain.Model.Cart;

import static com.emazon.ApiCart.Domain.Utils.DomConstants.FIRST;

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
            throw new CartIsNullException();
        }

        if (cart.getItem().isEmpty() || cart.getQuantity().isEmpty()) {
            throw new EmptyCartException();
        }
    }

    public static void validate(Cart cart, String user){
        validateCart(cart);
        validateItem(cart.getItem().get(FIRST));
        validateQuantity(cart.getQuantity().get(FIRST));
        validateUser(user);
    }

    public static void validate(long id, String user){
        validateItem(id);
        validateUser(user);
    }

    public static void validate(Cart cart){
        validateCart(cart);

    }
}
