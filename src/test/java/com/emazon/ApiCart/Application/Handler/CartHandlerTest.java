package com.emazon.ApiCart.Application.Handler;

import com.emazon.ApiCart.Application.Request.CartRequest;
import com.emazon.ApiCart.Application.Response.CartResponse;
import com.emazon.ApiCart.Domain.Model.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CartHandlerTest {

    private CartHandler cartHandler;

    private CartRequest request;
    private Cart cart;

    @BeforeEach
    void setUp() {
        cartHandler = Mappers.getMapper(CartHandler.class);

        request = new CartRequest();
        request.setItemId(1L);
        request.setQuantity(2);

        cart = new Cart();
        cart.setItem(List.of(1L, 2L));
        cart.setQuantity(List.of(3L, 4L));

    }

    @Test
    void toCart_MapsCartRequestToCart() {

        Cart response = cartHandler.toCart(request);

        assertEquals(1, response.getItem().size());
        assertTrue(response.getItem().contains(1L));
        assertEquals(2, response.getQuantity().get(0));
    }

    @Test
    void convertIdToList_ReturnsSingleItemList() {
        List<Long> idList = cartHandler.convertIdToList(5L);

        assertEquals(1, idList.size());
        assertTrue(idList.contains(5L));
    }
}
