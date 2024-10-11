package com.emazon.ApiCart.Application.Handler;

import com.emazon.ApiCart.Application.Request.CartRequest;
import com.emazon.ApiCart.Application.Response.CartResponse;
import com.emazon.ApiCart.Domain.Model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CartHandler {

    @Mapping(source = "itemId", target = "item", qualifiedByName = "convertIdToList")
    @Mapping(source = "quantity", target = "quantity", qualifiedByName = "convertIdToList")
    Cart toCart(CartRequest request);

    @Mapping(target = "item", ignore = true)
    CartResponse toResponse(Cart cart);

    @Named("convertIdToList")
    default List<Long> convertIdToList(long id) {
        return Collections.singletonList(id);
    }
}

