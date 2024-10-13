package com.emazon.ApiCart.Application.Service;

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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.emazon.ApiCart.Application.Utils.AppConstants.ZERO;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartServicePort cartsServicePort;
    private final CartHandler cartHandler;
    private final ItemServicePort itemServicePort;
    private final ItemHandler itemHandler;
    private final SorterUtil sorterUtil;
    private final FilterUtil filterUtil;
    private final PaginationUtil paginationUtil;

    public CartResponse addToCart(CartRequest request) {
        Cart cart = cartsServicePort.addToCart(cartHandler.toCart(request));
        return this.addItemToCart(cart);
    }

    public CartResponse deleteFromCart(long itemId) {
        Cart cart = cartsServicePort.deleteFromCart(itemId);
        return this.addItemToCart(cart);
    }

    public CartResponse listAllCartItems(String order, int page, int size) {
        Cart cart = cartsServicePort.listAllCartItems();
        CartResponse response = this.addItemToCart(cart);
        List<ItemAuxDto> items = sorterUtil.orderItems(response.getItem().getContent(), order);

        response.setItem(paginationUtil.getItemsPagination(order, page, size, items));
        return response;
    }

    public CartResponse listAllCartItems(String order, String filter, String name, int page, int size) {
        Cart cart = cartsServicePort.listAllCartItems();
        CartResponse response = this.addItemToCart(cart);

        List<ItemAuxDto> filteredItems = filterUtil.filterItems(response.getItem().getContent(), filter, name);
        List<ItemAuxDto> sortedItems = sorterUtil.orderItems(filteredItems, order);

        response.setItem(paginationUtil.getItemsPagination(order, page, size, sortedItems));
        return response;
    }

    private CartResponse addItemToCart(Cart cart) {
        CartResponse response = cartHandler.toResponse(cart);

        List<ItemAuxDto> items = itemServicePort.getItemList(cart.getItem())
                .stream()
                .map(itemHandler::toItemAux)
                .toList();

        double total = ZERO;
        for (int i = ZERO; i < items.size(); i++) {
            Long quantity = cart.getQuantity().get(i);
            Double price = items.get(i).getPrice();

            items.get(i).setStock(items.get(i).getQuantity());
            items.get(i).setPrice(quantity * price);
            items.get(i).setQuantity(quantity);

            total += quantity * price;
        }
        response.setItem(new PageImpl<>(items));
        response.setTotal(total);
        return response;
    }
}
