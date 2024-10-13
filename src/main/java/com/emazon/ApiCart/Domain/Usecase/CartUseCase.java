package com.emazon.ApiCart.Domain.Usecase;

import com.emazon.ApiCart.Domain.Api.CartServicePort;
import com.emazon.ApiCart.Domain.Model.Cart;
import com.emazon.ApiCart.Domain.Spi.CartPersistencePort;
import com.emazon.ApiCart.Domain.Spi.StockFeignPort;
import com.emazon.ApiCart.Domain.Spi.UserJwtPort;
import com.emazon.ApiCart.Domain.Utils.Validations;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.emazon.ApiCart.Domain.Utils.DomConstants.*;

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
        long currentItemId = cart.getItem().get(FIRST);
        long currentQuantity = cart.getQuantity().get(FIRST);


        if (repository == null) {

            stockFeignPort.checkStock(currentItemId, currentQuantity);
            return createCart(cart, userId);
        }

        int index = repository.getItem().indexOf(currentItemId);
        if (index != NOT_FOUND) {
            currentQuantity += repository.getQuantity().get(index);
        }

        stockFeignPort.checkStock(currentItemId, currentQuantity);

        Cart currentItem = setItem(repository, index, currentItemId, currentQuantity);
        currentItem.setActualizationDate(LocalDate.now().toString());
        return cartPersistencePort.addToCart(currentItem);

    }

    public Cart createCart(Cart cart, long userId) {

        cart.setUserId(userId);
        cart.setCreationDate(LocalDate.now().toString());
        cart.setActualizationDate(LocalDate.now().toString());

        return cartPersistencePort.addToCart(cart);
    }

    private Cart setItem(Cart cart, int index, long item, long quantity) {


        if (index != NOT_FOUND) {
            cart.getQuantity().set(index, quantity);
        } else {
            cart.getItem().add(item);
            cart.getQuantity().add(quantity);
        }
        return cart;
    }

    @Override
    public Cart deleteFromCart(long itemId) {

        String user = userJwt.extractUserId();
        Validations.validate(itemId, user);

        long userId = Long.parseLong(user);
        Cart cart = cartPersistencePort.getCart(userId);

        Validations.validate(cart);

        cart.setActualizationDate(LocalDate.now().toString());
        return cartPersistencePort.deleteFromCart(cart,itemId);
    }

    @Override
    public Cart listAllCartItems() {
        long userId = Long.parseLong(userJwt.extractUserId());

        Cart cart = cartPersistencePort.listAllCartItems(userId);
        Validations.validate(cart);

        return cart;
    }

    @Override
    public Cart buy() {
        String user = userJwt.extractUserId();
        Validations.validate(user);
        long userId = Long.parseLong(user);
        Cart cart = cartPersistencePort.getCart(userId);
        cart.setItem(new ArrayList<>());
        return cartPersistencePort.addToCart(cart);
    }

}
