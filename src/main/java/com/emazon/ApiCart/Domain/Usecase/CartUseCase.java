package com.emazon.ApiCart.Domain.Usecase;

import com.emazon.ApiCart.Domain.Api.CartServicePort;
import com.emazon.ApiCart.Domain.Model.Cart;
import com.emazon.ApiCart.Domain.Spi.CartPersistencePort;
import com.emazon.ApiCart.Domain.Spi.StockFeignPort;
import com.emazon.ApiCart.Domain.Spi.UserJwtPort;
import com.emazon.ApiCart.Domain.Utils.Validations;

import java.time.LocalDate;

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

        String user = userJwt.extractUserId();
        Validations.validate(cart,user);

        long userId = Long.parseLong(user);
        Cart repository = cartPersistencePort.getCart(userId);
        Long currentItemId = cart.getItem().get(0);
        Long currentQuantity = cart.getQuantity().get(0);

        if (repository == null) {
            stockFeignPort.checkStock(currentItemId,currentQuantity);
            return createCart(cart, userId);
        }

        int index = repository.getItem().indexOf(currentItemId);
        if (index != -1) {
            currentQuantity += repository.getQuantity().get(index);
            repository.getQuantity().set(index, currentQuantity);
        } else {
            repository.getItem().add(currentItemId);
            repository.getQuantity().add(currentQuantity);
        }

        stockFeignPort.checkStock(currentItemId, currentQuantity );
        repository.setActualizationDate(LocalDate.now().toString());
        return cartPersistencePort.addToCart(repository);
    }

    public Cart createCart(Cart cart, long userId) {

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
    public Cart listAllCartItems() {
        long userId = Long.parseLong(userJwt.extractUserId());
        return cartPersistencePort.listAllCartItems(userId);
    }

    @Override
    public Cart listAllCartItems(String filter) {
        long userId = Long.parseLong(userJwt.extractUserId());

        return cartPersistencePort.listAllCartItems(userId);
    }
}
