package com.emazon.ApiCart.Domain.Usecase;

import com.emazon.ApiCart.Domain.Api.CartServicePort;
import com.emazon.ApiCart.Domain.Model.Cart;
import com.emazon.ApiCart.Domain.Spi.CartPersistencePort;
import com.emazon.ApiCart.Domain.Spi.StockFeignPort;
import com.emazon.ApiCart.Domain.Spi.UserJwtPort;

import java.time.LocalDate;
import java.util.List;

public class CartUseCase implements CartServicePort {

    private final CartPersistencePort cartPersistencePort;
    private final UserJwtPort userJwt;
    private final StockFeignPort stockFeignPort;

    public CartUseCase(CartPersistencePort cartPersistencePort, UserJwtPort userJwt, StockFeignPort stockFeignPort) {
        this.cartPersistencePort = cartPersistencePort;
        this.userJwt = userJwt;
        this.stockFeignPort = stockFeignPort;
    }

    @Override
    public Cart addToCart(Cart cart) {
        long userId = Long.parseLong(userJwt.extractUserId());
        Cart repository = cartPersistencePort.getCart(userId);

        if (repository == null) {
            return createCart(cart, userId);
        }
        if (!repository.getItemId().contains(cart.getItemId().get(0))) {
            repository.getItemId().add(cart.getItemId().get(0));
        }
        repository.setQuantity(repository.getQuantity() + cart.getQuantity());
        repository.setActualizationDate(LocalDate.now().toString());
        return cartPersistencePort.addToCart(repository);
    }

    public Cart createCart(Cart cart, long userId) {

        stockFeignPort.getById(cart.getItemId().get(0));

        cart.setUserId(userId);
        cart.setCreationDate(LocalDate.now().toString());
        cart.setActualizationDate(LocalDate.now().toString());
        return cartPersistencePort.addToCart(cart);
    }

    @Override
    public Cart deleteFromCart(long itemId) {
        long userId = Long.parseLong(userJwt.extractUserId());
        return cartPersistencePort.deleteFromCart(userId,itemId);
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
