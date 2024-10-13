package com.emazon.ApiCart.Application.Service;

import static org.junit.jupiter.api.Assertions.*;
import com.emazon.ApiCart.Application.Handler.CartHandler;
import com.emazon.ApiCart.Application.Handler.ItemHandler;
import com.emazon.ApiCart.Application.Request.CartRequest;
import com.emazon.ApiCart.Application.Response.CartResponse;
import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import com.emazon.ApiCart.Application.Utils.FilterUtil;
import com.emazon.ApiCart.Application.Utils.SorterUtil;
import com.emazon.ApiCart.Application.Utils.PaginationUtil;
import com.emazon.ApiCart.Domain.Api.CartServicePort;
import com.emazon.ApiCart.Domain.Api.ItemServicePort;
import com.emazon.ApiCart.Domain.Model.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartServicePort cartServicePort;
    @Mock
    private CartHandler cartHandler;
    @Mock
    private ItemServicePort itemServicePort;
    @Mock
    private ItemHandler itemHandler;
    @Mock
    private SorterUtil sorterUtil;
    @Mock
    private FilterUtil filterUtil;
    @Mock
    private PaginationUtil paginationUtil;

    @InjectMocks
    private CartService cartService;

    private Cart cart;
    private CartResponse response;
    private CartRequest cartRequest;
    private List<ItemAuxDto> filteredItems;
    private List<ItemAuxDto> sortedItems;
    private PageImpl<ItemAuxDto> paginatedItems;  // Assume PageImpl is used for pagination
    private List<ItemAuxDto> items;
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        cartRequest = new CartRequest();
        response = new CartResponse();
        cart = new Cart();
        items = List.of(new ItemAuxDto(), new ItemAuxDto());

        filteredItems = List.of(new ItemAuxDto(), new ItemAuxDto());
        sortedItems = List.of(new ItemAuxDto(), new ItemAuxDto());
        paginatedItems = new PageImpl<>(sortedItems);

    }

    @Test
    void addToCart_Success() {

        CartResponse expectedResponse = new CartResponse();
        when(cartHandler.toCart(cartRequest)).thenReturn(cart);
        when(cartServicePort.addToCart(cart)).thenReturn(cart);
        when(cartHandler.toResponse(cart)).thenReturn(expectedResponse);

        CartResponse result = cartService.addToCart(cartRequest);

        assertEquals(expectedResponse, result);
        verify(cartServicePort).addToCart(cart);
        verify(cartHandler).toCart(cartRequest);
    }

    @Test
    void deleteFromCart_Success() {

        long itemId = 1L;
        CartResponse expectedResponse = new CartResponse();
        when(cartServicePort.deleteFromCart(itemId)).thenReturn(cart);
        when(cartHandler.toResponse(cart)).thenReturn(expectedResponse);

        CartResponse result = cartService.deleteFromCart(itemId);

        assertEquals(expectedResponse, result);
        verify(cartServicePort).deleteFromCart(itemId);
        verify(cartHandler).toResponse(cart);
    }

    @Test
    void listAllCart_Success() {

        when(cartServicePort.listAllCartItems()).thenReturn(cart);
        when(cartHandler.toResponse(cart)).thenReturn(response);
        when(sorterUtil.orderItems(anyList(), anyString())).thenReturn(items);
        when(paginationUtil.getItemsPagination(anyString(), anyInt(), anyInt(), anyList())).thenReturn(new PageImpl<>(items));

        CartResponse result = cartService.listAllCart("asc", 0, 5);

        assertNotNull(result);
        verify(cartServicePort).listAllCartItems();
        verify(sorterUtil).orderItems(anyList(), anyString());
        verify(paginationUtil).getItemsPagination(anyString(), anyInt(), anyInt(), anyList());
    }
    @Test
    void listAllCart_WithFilterAndPagination_Success() {
        // Arrange
        String order = "asc";
        String filter = "name";
        String name = "itemName";
        int page = 0;
        int size = 5;


        when(cartServicePort.listAllCartItems()).thenReturn(cart);
        when(cartHandler.toResponse(cart)).thenReturn(response);
        when(filterUtil.filterItems(anyList(), eq(filter), eq(name))).thenReturn(filteredItems);
        when(sorterUtil.orderItems(filteredItems, order)).thenReturn(sortedItems);
        when(paginationUtil.getItemsPagination(order, page, size, sortedItems)).thenReturn(paginatedItems);

        CartResponse result = cartService.listAllCart(order, filter, name, page, size);

        assertNotNull(result);
        assertEquals(paginatedItems, result.getItem());
        verify(cartServicePort).listAllCartItems();
        verify(filterUtil).filterItems(anyList(), eq(filter), eq(name));
        verify(sorterUtil).orderItems(filteredItems, order);
        verify(paginationUtil).getItemsPagination(order, page, size, sortedItems);
    }


}
