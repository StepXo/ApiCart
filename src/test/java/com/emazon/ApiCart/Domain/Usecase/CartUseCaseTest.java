package com.emazon.ApiCart.Domain.Usecase;

import com.emazon.ApiCart.Domain.Exeptions.*;
import com.emazon.ApiCart.Domain.Model.Cart;
import com.emazon.ApiCart.Domain.Spi.CartPersistencePort;
import com.emazon.ApiCart.Domain.Spi.StockFeignPort;
import com.emazon.ApiCart.Domain.Spi.UserJwtPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartUseCaseTest {

    @Mock
    private CartPersistencePort cartPersistencePort;

    @Mock
    private UserJwtPort userJwt;

    @Mock
    private StockFeignPort stockFeignPort;

    @InjectMocks
    private CartUseCase cartUseCase;

    private Cart cart;
    private Cart newCart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cart = new Cart();
        cart.setItem(new ArrayList<>(List.of(1L)));
        cart.setQuantity(new ArrayList<>(List.of(1L)));
        cart.setUserId(1L);
        cart.setCreationDate(LocalDate.now().toString());
        cart.setActualizationDate(LocalDate.now().toString());

        newCart = new Cart();
        newCart.setItem(new ArrayList<>(List.of(1L,2L)));
        newCart.setQuantity(new ArrayList<>(List.of(1L,5L)));
        newCart.setUserId(1L);
        newCart.setCreationDate(LocalDate.now().toString());
        newCart.setActualizationDate(LocalDate.now().toString());

    }

    @Test
    void addToCart_createsNewCart() {
        when(userJwt.extractUserId()).thenReturn("1");
        when(cartPersistencePort.getCart(1L)).thenReturn(null);
        doNothing().when(stockFeignPort).checkStock(1L, 2L);
        when(cartPersistencePort.addToCart(any(Cart.class))).thenReturn(cart);

        Cart createdCart = cartUseCase.addToCart(cart);

        assertNotNull(createdCart);
        assertEquals(1L, createdCart.getUserId());
        assertEquals(1, createdCart.getItem().size());
        verify(cartPersistencePort, times(1)).addToCart(any(Cart.class));
    }

    @Test
    void addToCart_updatesExistingCart() {
        when(userJwt.extractUserId()).thenReturn("1");
        when(cartPersistencePort.getCart(1L)).thenReturn(cart);
        doNothing().when(stockFeignPort).checkStock(2L, 5L);
        when(cartPersistencePort.addToCart(any(Cart.class))).thenReturn(newCart);

        Cart updatedCart = cartUseCase.addToCart(newCart);

        assertNotNull(updatedCart);
        assertEquals(5L, updatedCart.getQuantity().get(1));
        verify(cartPersistencePort, times(1)).addToCart(any(Cart.class));
    }

    @Test
    void addToCart_throwsQuantityIsNotEnough() {

        when(userJwt.extractUserId()).thenReturn("1");
        when(cartPersistencePort.getCart(1L)).thenReturn(null);
        doThrow(new QuantityIsNotEnough(errorMessage)).when(stockFeignPort).checkStock(anyLong(), anyLong());

        assertThrows(QuantityIsNotEnough.class, () -> cartUseCase.addToCart(cart));
        verify(stockFeignPort, times(1)).checkStock(anyLong(), anyLong());

    }

    @Test
    void deleteFromCart_deletesItemFromCart() {

        when(userJwt.extractUserId()).thenReturn("1");
        when(cartPersistencePort.getCart(1L)).thenReturn(newCart);
        when(cartPersistencePort.deleteFromCart(any(Cart.class), eq(2L))).thenReturn(cart);
        Cart updatedCart = cartUseCase.deleteFromCart(2L);

        assertEquals(1L, updatedCart.getUserId());
        assertEquals(1, updatedCart.getItem().size());
        verify(cartPersistencePort, times(1)).deleteFromCart(any(Cart.class), eq(2L));
    }

    @Test
    void deleteFromCart_EmptyCartException() {
        when(userJwt.extractUserId()).thenReturn("1");
        Cart emptyCart = new Cart();
        emptyCart.setItem(new ArrayList<>());
        when(cartPersistencePort.getCart(1L)).thenReturn(emptyCart);

        assertThrows(EmptyCartException.class, () -> cartUseCase.deleteFromCart(1L));
    }

    @Test
    void listAllCartItems_returnsCart() {

        when(userJwt.extractUserId()).thenReturn("1");
        when(cartPersistencePort.listAllCartItems(1L)).thenReturn(cart);

        Cart result = cartUseCase.listAllCartItems();

        assertNotNull(result);
        assertFalse(result.getItem().isEmpty());
        verify(cartPersistencePort, times(1)).listAllCartItems(1L);
    }

    @Test
    void listAllCartItems_EmptyCartException() {
        when(userJwt.extractUserId()).thenReturn("1");
        Cart emptyCart = new Cart(); // Carrito vacío en vez de null
        emptyCart.setItem(new ArrayList<>());
        when(cartPersistencePort.listAllCartItems(1L)).thenReturn(emptyCart);

        assertThrows(EmptyCartException.class, () -> cartUseCase.listAllCartItems());
    }

    @Test
    void addToCart_ItemIsInvalid() {
        // Arrange
        when(userJwt.extractUserId()).thenReturn("1");
        when(cartPersistencePort.getCart(1L)).thenReturn(cart);
        doThrow(new InvalidItemException()).when(stockFeignPort).checkStock(anyLong(), anyLong());

        // Act & Assert
        assertThrows(InvalidItemException.class, () -> {
            cartUseCase.addToCart(cart);
        });

        verify(stockFeignPort, times(1)).checkStock(anyLong(), anyLong());
    }

    @Test
    void addToCart_QuantityIsInvalid() {
        cart.getQuantity().set(0, -1L);
        when(userJwt.extractUserId()).thenReturn("1");

        assertThrows(InvalidQuantityException.class, () -> {
            cartUseCase.addToCart(cart);
        });

        verify(cartPersistencePort, times(0)).getCart(1L);
    }

    @Test
    void addToCart_CartIsNull() {
        // Arrange
        when(userJwt.extractUserId()).thenReturn("1");

        // Act & Assert
        assertThrows(CartIsNullException.class, () -> {
            cartUseCase.addToCart(null);
        });

        verify(cartPersistencePort, times(0)).getCart(1L);
    }

    @Test
    void deleteFromCart_UserIdIsInvalid() {
        // Arrange
        when(userJwt.extractUserId()).thenReturn("");

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> {
            cartUseCase.deleteFromCart(1L);
        });

        verify(userJwt, times(1)).extractUserId();
    }

    @Test
    void deleteFromCart_CartIsNull() {
        // Arrange
        when(userJwt.extractUserId()).thenReturn("1");
        when(cartPersistencePort.getCart(1L)).thenReturn(null);

        // Act & Assert
        assertThrows(CartIsNullException.class, () -> {
            cartUseCase.deleteFromCart(1L);
        });

        verify(cartPersistencePort, times(1)).getCart(1L);
    }

    @Test
    void addToCart_ItemIdIsInvalid() {

        cart.getItem().set(0, 0L);
        when(userJwt.extractUserId()).thenReturn("1");

        assertThrows(InvalidItemException.class, () -> {
            cartUseCase.addToCart(cart);
        });

        verify(cartPersistencePort, times(0)).getCart(1L);
    }

}

