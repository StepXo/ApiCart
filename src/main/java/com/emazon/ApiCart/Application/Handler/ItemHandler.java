package com.emazon.ApiCart.Application.Handler;

import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import com.emazon.ApiCart.Domain.Model.Item;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemHandler {

    ItemAuxDto toItemAux(Item request);


}

