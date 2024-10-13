package com.emazon.ApiCart.Application.Handler;

import com.emazon.ApiCart.Application.Request.CartRequest;
import com.emazon.ApiCart.Application.Response.CartResponse;
import com.emazon.ApiCart.Application.Utils.AppConstants;
import com.emazon.ApiCart.Domain.Model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


import java.util.Collections;
import java.util.List;

@Mapper(componentModel = AppConstants.SPRING)
public interface CartHandler {

    @Mapping(source = AppConstants.ITEM_ID, target = AppConstants.ITEM, qualifiedByName = AppConstants.MAP_TO_LIST)
    @Mapping(source = AppConstants.QUANTITY, target = AppConstants.QUANTITY, qualifiedByName = AppConstants.MAP_TO_LIST)
    Cart toCart(CartRequest request);

    @Mapping(target = AppConstants.ITEM,ignore = true)
    @Mapping(target = AppConstants.TOTAL,ignore = true)
    CartResponse toResponse(Cart cart);

    @Named(AppConstants.MAP_TO_LIST)
    default List<Long> convertIdToList(long id) {
        return Collections.singletonList(id);
    }

}

