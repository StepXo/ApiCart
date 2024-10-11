package com.emazon.ApiCart.Application.Service;

import com.emazon.ApiCart.Application.Handler.CartHandler;
import com.emazon.ApiCart.Application.Request.CartRequest;
import com.emazon.ApiCart.Application.Response.CartResponse;
import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import com.emazon.ApiCart.Domain.Api.CartServicePort;
import com.emazon.ApiCart.Domain.Model.Cart;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartServicePort cartsServicePort;
    private final CartHandler cartHandler;

    public CartResponse addToCart(CartRequest request) {
        Cart cart = cartsServicePort.addToCart(cartHandler.toCart(request));
        return this.addItemToCart(cart);
    }

    public CartResponse deleteFromCart(long itemId) {
        Cart cart = cartsServicePort.deleteFromCart(itemId);
        return this.addItemToCart(cart);
    }

    public CartResponse listAllCartItems(){
        Cart cart = cartsServicePort.listAllCartItems();

        return this.addItemToCart(cart);

    }

    public CartResponse listAllCartItems(String filter){
        Cart cart = cartsServicePort
                .listAllCartItems(filter);

        return this.addItemToCart(cart);

    }

    private CartResponse addItemToCart(Cart cart) {

        CartResponse response = cartHandler.toResponse(cart);
        List<ItemAuxDto> items = new ArrayList<>();
        for (int i = 0; i < cart.getItem().size(); i++) {
            Long itemId = cart.getItem().get(i);
            Long quantity = cart.getQuantity().get(i);
            Double price = cart.getPrice().get(i);
            items.add(new ItemAuxDto(itemId, quantity, price));
        }

        response.setItem(items);
        return response;
    }
}
