package com.emazon.ApiCart.Domain.Api;

import com.emazon.ApiCart.Domain.Model.Cart;

import java.util.List;

public interface CartServicePort {
    Cart addToCart(Cart cart);
    Cart deleteFromCart(long itemId);
    Cart listAllCartItems();
    Cart listAllCartItems(String filter);


}
