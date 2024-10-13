package com.emazon.ApiCart.Application.Handler;

import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import com.emazon.ApiCart.Application.Utils.AppConstants;
import com.emazon.ApiCart.Domain.Model.Item;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = AppConstants.SPRING)
public interface ItemHandler {

    ItemAuxDto toItemAux(Item request);


}

