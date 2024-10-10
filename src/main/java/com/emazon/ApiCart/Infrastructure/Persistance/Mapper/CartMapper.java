package com.emazon.ApiCart.Infrastructure.Persistance.Mapper;

import com.emazon.ApiCart.Domain.Model.Cart;
import com.emazon.ApiCart.Infrastructure.Persistance.Entity.CartEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface CartMapper {
    Cart toCart(CartEntity entity);
    CartEntity toCartEntity(Cart cart);
}
