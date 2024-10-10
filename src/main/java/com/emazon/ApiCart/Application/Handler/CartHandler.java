package com.emazon.ApiCart.Application.Handler;

import com.emazon.ApiCart.Application.Request.CartRequest;
import com.emazon.ApiCart.Application.Response.CartResponse;
import com.emazon.ApiCart.Domain.Model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface CartHandler {
    Cart toCart(CartRequest request);
    CartResponse toResponse(Cart cart);

}
