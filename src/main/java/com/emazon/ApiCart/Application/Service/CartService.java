package com.emazon.ApiCart.Application.Service;

import com.emazon.ApiCart.Application.Handler.CartHandler;
import com.emazon.ApiCart.Application.Request.CartRequest;
import com.emazon.ApiCart.Application.Response.CartResponse;
import com.emazon.ApiCart.Domain.Api.CartServicePort;
import com.emazon.ApiCart.Domain.Model.Cart;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartServicePort cartsServicePort;
    private final CartHandler cartHandler;

    public CartResponse addToCart(CartRequest request) {
        Cart cart = cartsServicePort.addToCart(cartHandler.toCart(request));
        return cartHandler.toResponse(cart);
    }

    public CartResponse deleteFromCart(long itemId) {
        Cart cart = cartsServicePort.deleteFromCart(itemId);
        return cartHandler.toResponse(cart);
    }

    public List<CartResponse> listAllCartItems(){
        List<CartResponse> cartResponses = cartsServicePort
                .listAllCartItems()
                .stream()
                .map(cartHandler::toResponse)
                .toList();

        return cartResponses;
    }

    public List<CartResponse> listAllCartItems(String filter){
        List<CartResponse> cartResponses = cartsServicePort
                .listAllCartItems(filter)
                .stream()
                .map(cartHandler::toResponse)
                .toList();

        return cartResponses;    }

}
