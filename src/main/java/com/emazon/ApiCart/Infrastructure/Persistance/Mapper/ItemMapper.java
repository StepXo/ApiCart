package com.emazon.ApiCart.Infrastructure.Persistance.Mapper;


import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import com.emazon.ApiCart.Domain.Model.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface ItemMapper {
    Item toItem(ItemAuxDto entity);
    ItemAuxDto toItemAuxDto(Item item);
}
