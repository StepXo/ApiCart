package com.emazon.ApiCart.Infrastructure.Persistance.Mapper;

import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import com.emazon.ApiCart.Domain.Model.Item;
import org.mapstruct.Mapper;

import static com.emazon.ApiCart.Infrastructure.Utils.InfraConstants.SPRING;

@Mapper(componentModel = SPRING)
public interface ItemMapper {

    Item toItem(ItemAuxDto request);

}
