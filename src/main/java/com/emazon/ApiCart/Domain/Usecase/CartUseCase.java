package com.emazon.ApiCart.Domain.Usecase;

import com.emazon.ApiCart.Domain.Api.CartServicePort;
import com.emazon.ApiCart.Domain.Model.Cart;
import com.emazon.ApiCart.Domain.Spi.CartPersistencePort;
import com.emazon.ApiCart.Domain.Spi.StockFeignPort;
import com.emazon.ApiCart.Domain.Spi.UserJwtPort;
import com.emazon.ApiCart.Domain.Utils.Validations;

import java.time.LocalDate;
import java.util.Collections;

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
            double price = stockFeignPort.checkStock(currentItemId, currentQuantity);
            return createCart(cart, userId, price);
        }
        int index = repository.getItem().indexOf(currentItemId);
        System.out.println(index);

        Cart cartQuantity = this.setQuantity(index, repository, currentItemId, currentQuantity);

        double price = stockFeignPort.checkStock(currentItemId, cartQuantity.getQuantity().get(0));
        Cart cartPrice = this.setPrice(index, repository, currentQuantity, price);

        cartPrice.setActualizationDate(LocalDate.now().toString());
        return cartPersistencePort.addToCart(cartPrice);
    }

    public Cart createCart(Cart cart, long userId, double price) {

        cart.setUserId(userId);
        cart.setPrice(Collections.singletonList(price*cart.getQuantity().get(0)));
        cart.setCreationDate(LocalDate.now().toString());
        cart.setActualizationDate(LocalDate.now().toString());

        return cartPersistencePort.addToCart(cart);
    }

    @Override
    public Cart deleteFromCart(long itemId) {

        String user = userJwt.extractUserId();
        Validations.validate(itemId, user);

        long userId = Long.parseLong(user);
        Cart cart = cartPersistencePort.getCart(userId);

        cart.setActualizationDate(LocalDate.now().toString());
        return cartPersistencePort.deleteFromCart(cart,itemId);
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

    private Cart setQuantity(int index,Cart cart,long itemId,long quantity){
        if (index != -1) {
            quantity += cart.getQuantity().get(index);
            cart.getQuantity().set(index, quantity);
        } else {
            cart.getItem().add(itemId);
            cart.getQuantity().add(quantity);
        }


        return cart;
    }

    private Cart setPrice(int index,Cart cart,long quantity, double price){
        if (index != -1) {
            price = cart.getPrice().get(index) + price * quantity;
            cart.getPrice().set(index, price);
        } else {
            cart.getPrice().add(price*quantity);
        }
        return cart;
    }
}
