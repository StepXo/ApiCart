package com.emazon.ApiCart.Application.Handler;

import com.emazon.ApiCart.Application.Request.CartRequest;
import com.emazon.ApiCart.Application.Response.CartResponse;
import com.emazon.ApiCart.Domain.Model.Cart;
import com.emazon.ApiCart.Domain.Model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "Spring")
public interface CartHandler {

    @Mapping(source = "itemId", target = "itemId", qualifiedByName = "CONVERT_TO_ITEM_LIST")
    Cart toCart(CartRequest request);

    CartResponse toResponse(Cart cart);

    @Named("CONVERT_TO_ITEM_LIST")
    default List<Item> convertItemIdToList(long itemId) {
        return Collections.singletonList(new Item(itemId,0,0));
    }
}
