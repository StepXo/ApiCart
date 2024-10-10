package com.emazon.ApiCart.Domain.Usecase;

import com.emazon.ApiCart.Domain.Api.CartServicePort;
import com.emazon.ApiCart.Domain.Model.Cart;
import com.emazon.ApiCart.Domain.Spi.CartPersistencePort;
import com.emazon.ApiCart.Domain.Spi.UserJwtPort;

import java.util.List;

public class CartUseCase implements CartServicePort {

    private final CartPersistencePort cartPersistencePort;
    private final UserJwtPort userJwt;

    public CartUseCase(CartPersistencePort cartPersistencePort, UserJwtPort userJwt) {
        this.cartPersistencePort = cartPersistencePort;
        this.userJwt = userJwt;
    }

    @Override
    public Cart addToCart(Cart cart) {
        long userId = Long.parseLong(userJwt.extractUserId());

        Cart repository = cartPersistencePort.getCart(userId);
        if (repository == null) {
            return createCart(cart, userId);
        }
        return cartPersistencePort.addToCart(repository);
    }

    public Cart createCart(Cart cart, long userId) {
        return cartPersistencePort.addToCart(cart);
    }

    @Override
    public Cart deleteFromCart(long itemId) {
        return cartPersistencePort.deleteFromCart(itemId);
    }

    @Override
    public List<Cart> listAllCartItems() {
        return cartPersistencePort.listAllCartItems();
    }

    @Override
    public List<Cart> listAllCartItems(String filter) {
        return cartPersistencePort.listAllCartItems();
    }
}
