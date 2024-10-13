package com.emazon.ApiCart.Infrastructure.Adapters.Jpa;

import com.emazon.ApiCart.Domain.Model.Cart;
import com.emazon.ApiCart.Infrastructure.Persistance.Entity.CartEntity;
import com.emazon.ApiCart.Infrastructure.Persistance.Mapper.CartMapper;
import com.emazon.ApiCart.Infrastructure.Persistance.Repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartJpaTest {

    @Mock
    private CartMapper cartMapperMock;  // Mock the CartMapper

    @Mock
    private CartRepository cartRepositoryMock;  // Mock the CartRepository

    @InjectMocks
    private CartJpa cartJpa;  // Inject mocks into CartJpa

    private Cart cart;
    private CartEntity cartEntity;
    long userId;
    long itemId;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cart = new Cart();
        cartEntity = new CartEntity();
        userId = 1L;
        itemId = 1L;

    }

    @Test
    void addToCart_returnsCart_whenSavedSuccessfully() {
        when(cartMapperMock.toCartEntity(cart)).thenReturn(cartEntity);
        when(cartRepositoryMock.save(cartEntity)).thenReturn(cartEntity);
        when(cartMapperMock.toCart(cartEntity)).thenReturn(cart);

        Cart result = cartJpa.addToCart(cart);

        assertEquals(cart, result);
        verify(cartMapperMock, times(1)).toCartEntity(cart);
        verify(cartRepositoryMock, times(1)).save(cartEntity);
        verify(cartMapperMock, times(1)).toCart(cartEntity);
    }

    @Test
    void deleteFromCart_removesItemAndReturnsUpdatedCart() {
        cartEntity.setItem(new ArrayList<>(List.of(itemId)));
        when(cartMapperMock.toCartEntity(cart)).thenReturn(cartEntity);
        when(cartRepositoryMock.save(cartEntity)).thenReturn(cartEntity);
        when(cartMapperMock.toCart(cartEntity)).thenReturn(cart);

        Cart result = cartJpa.deleteFromCart(cart, itemId);

        assertFalse(cartEntity.getItem().contains(itemId));
        assertEquals(cart, result);
        verify(cartMapperMock, times(1)).toCartEntity(cart);
        verify(cartRepositoryMock, times(1)).save(cartEntity);
        verify(cartMapperMock, times(1)).toCart(cartEntity);
    }

    @Test
    void listAllCartItems_returnsCart_whenCartExistsForUser() {
        when(cartRepositoryMock.findByUserId(userId)).thenReturn(Optional.of(cartEntity));
        when(cartMapperMock.toCart(cartEntity)).thenReturn(cart);

        Cart result = cartJpa.listAllCartItems(userId);

        assertEquals(cart, result);
        verify(cartRepositoryMock, times(1)).findByUserId(userId);
        verify(cartMapperMock, times(1)).toCart(cartEntity);
    }

    @Test
    void listAllCartItems_returnsNull_whenNoCartExistsForUser() {
        when(cartRepositoryMock.findByUserId(userId)).thenReturn(Optional.empty());

        Cart result = cartJpa.listAllCartItems(userId);

        assertNull(result);
        verify(cartRepositoryMock, times(1)).findByUserId(userId);
        verify(cartMapperMock, never()).toCart(any());
    }

    @Test
    void getCart_returnsCart_whenCartExistsForUser() {
        when(cartRepositoryMock.findByUserId(userId)).thenReturn(Optional.of(cartEntity));
        when(cartMapperMock.toCart(cartEntity)).thenReturn(cart);

        Cart result = cartJpa.getCart(userId);

        assertEquals(cart, result);
        verify(cartRepositoryMock, times(1)).findByUserId(userId);
        verify(cartMapperMock, times(1)).toCart(cartEntity);
    }

    @Test
    void getCart_returnsNull_whenNoCartExistsForUser() {
        when(cartRepositoryMock.findByUserId(userId)).thenReturn(Optional.empty());

        Cart result = cartJpa.getCart(userId);

        assertNull(result);
        verify(cartRepositoryMock, times(1)).findByUserId(userId);
        verify(cartMapperMock, never()).toCart(any());
    }
}
