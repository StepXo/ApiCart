package com.emazon.ApiCart.Infrastructure.Adapters.Jpa;

import com.emazon.ApiCart.Domain.Model.Cart;
import com.emazon.ApiCart.Domain.Spi.CartPersistencePort;
import com.emazon.ApiCart.Infrastructure.Persistance.Entity.CartEntity;
import com.emazon.ApiCart.Infrastructure.Persistance.Mapper.CartMapper;
import com.emazon.ApiCart.Infrastructure.Persistance.Repository.CartRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartJpa implements CartPersistencePort {
    private final CartMapper mapper;
    private final CartRepository cartRepository;

    @Override
    public Cart addToCart(Cart cart) {
        return mapper.toCart(cartRepository.save(mapper.toCartEntity(cart)));
    }

    @Override
    public Cart deleteFromCart(Cart cart,long itemId) {

        CartEntity cartEntity = mapper.toCartEntity(cart);
        cartEntity.getItem().remove(itemId);
        return mapper.toCart( cartRepository.save(cartEntity));
    }

    @Override
    public Cart listAllCartItems(long userId) {
        return cartRepository.findByUserId(userId)
                .map(mapper::toCart)
                .orElse(null);
    }

    @Override
    public Cart getCart(long userId) {
        return cartRepository.findByUserId(userId)
                .map(mapper::toCart)
                .orElse(null);
    }
}
