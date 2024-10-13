package com.emazon.ApiCart.Infrastructure.Persistance.Mapper;

import com.emazon.ApiCart.Domain.Model.Cart;
import com.emazon.ApiCart.Infrastructure.Persistance.Entity.CartEntity;
import org.mapstruct.Mapper;
import static com.emazon.ApiCart.Infrastructure.Utils.InfraConstants.SPRING;

@Mapper(componentModel = SPRING)
public interface CartMapper {
    Cart toCart(CartEntity cartEntity);
    CartEntity toCartEntity(Cart cart);
}
