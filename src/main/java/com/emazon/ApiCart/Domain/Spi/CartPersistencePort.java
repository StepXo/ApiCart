package com.emazon.ApiCart.Domain.Spi;

import com.emazon.ApiCart.Domain.Model.Cart;

import java.util.List;

public interface CartPersistencePort {
    Cart addToCart(Cart cart);
    Cart deleteFromCart(long userId,long itemId);
    Cart listAllCartItems(long userId);
    Cart getCart(long userId);
}
