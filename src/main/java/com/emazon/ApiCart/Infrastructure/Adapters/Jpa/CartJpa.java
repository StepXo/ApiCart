package com.emazon.ApiCart.Infrastructure.Adapters.Jpa;

import com.emazon.ApiCart.Domain.Model.Cart;
import com.emazon.ApiCart.Domain.Spi.CartPersistencePort;
import com.emazon.ApiCart.Infrastructure.Persistance.Entity.CartEntity;
import com.emazon.ApiCart.Infrastructure.Persistance.Mapper.CartMapper;
import com.emazon.ApiCart.Infrastructure.Persistance.Repository.CartRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CartJpa implements CartPersistencePort {
    private final CartMapper mapper;
    private final CartRepository cartRepository;

    @Override
    public Cart addToCart(Cart cart) {
        return mapper.toCart(cartRepository.save(mapper.toCartEntity(cart)));
    }

    @Override
    public Cart deleteFromCart(long itemId) {
        CartEntity cart = cartRepository.findByItemId(itemId).orElse(null);
        if (cart!= null) {
            cartRepository.deleteById(cart.getId());
        }
        return mapper.toCart(cart);
    }

    @Override
    public List<Cart> listAllCartItems() {
        return cartRepository
                .findAll()
                .stream()
                .map(mapper::toCart)
                .toList();
    }

    @Override
    public Cart getCart(long userId) {
        return cartRepository.findByUserId(userId)
                .map(mapper::toCart)
                .orElse(null);
    }
}
