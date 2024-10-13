package com.emazon.ApiCart.Infrastructure.Adapters.Feign;

import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import com.emazon.ApiCart.Domain.Exeptions.ItemNotFoundException;
import com.emazon.ApiCart.Domain.Exeptions.QuantityIsNotEnough;
import com.emazon.ApiCart.Domain.Model.Item;
import com.emazon.ApiCart.Infrastructure.Persistance.Mapper.ItemMapper;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StockFeignTest {

    @Mock
    private IStockFeign stockFeignMock;

    @Mock
    private ItemMapper itemMapperMock;

    @InjectMocks
    private StockFeign stockFeign;
    private long itemId;
    private long quantity;
    List<Long> itemIds;
    List<ItemAuxDto> itemList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemId = 1L;
        quantity = 5L;
        itemIds = List.of(1L, 2L);
        itemList =  Arrays.asList(
                setItemAuxDto(1L,"Item1",10L,100.0),
                setItemAuxDto(2L,"Item2",20L,200.0)
        );
    }
    private ItemAuxDto setItemAuxDto(long index,String name,long quantity,double price){
        ItemAuxDto item = new ItemAuxDto();
        item.setId(index);
        item.setName(name);
        item.setQuantity(quantity);
        item.setPrice(price);
        return item;
    }

    @Test
    void checkStock_FeignThrowsNotFound() {

        doThrow(FeignException.NotFound.class).when(stockFeignMock).checkStock(itemId, quantity);
        assertThrows(ItemNotFoundException.class, () -> stockFeign.checkStock(itemId, quantity));
        verify(stockFeignMock, times(1)).checkStock(itemId, quantity);
    }

    @Test
    void checkStock_FeignThrowsInternalServerError() {

        doThrow(FeignException.InternalServerError.class).when(stockFeignMock).checkStock(itemId, quantity);

        assertThrows(QuantityIsNotEnough.class, () -> stockFeign.checkStock(itemId, quantity));

        verify(stockFeignMock, times(1)).checkStock(itemId, quantity);
    }

    @Test
    void checkStock_FeignThrowsBadRequest() {

        doThrow(FeignException.BadRequest.class).when(stockFeignMock).checkStock(itemId, quantity);

        assertThrows(QuantityIsNotEnough.class, () -> stockFeign.checkStock(itemId, quantity));

        verify(stockFeignMock, times(1)).checkStock(itemId, quantity);
    }

    @Test
    void checkStock_FeignSuccess() {

        stockFeign.checkStock(itemId, quantity);

        verify(stockFeignMock, times(1)).checkStock(itemId, quantity);
    }

    @Test
    void getItemsByList_ReturnsItems() {

        when(stockFeignMock.getItemsByList(itemIds)).thenReturn(itemList);
        when(itemMapperMock.toItem(any(ItemAuxDto.class))).thenReturn(new Item());

        stockFeign.getItemsByList(itemIds);

        verify(stockFeignMock, times(1)).getItemsByList(itemIds);
        verify(itemMapperMock, times(2)).toItem(any(ItemAuxDto.class));
    }

    @Test
    void getItemsByList_ThrowsError() {
        when(stockFeignMock.getItemsByList(itemIds)).thenThrow(FeignException.class);

        assertThrows(FeignException.class, () -> stockFeign.getItemsByList(itemIds));

        verify(stockFeignMock, times(1)).getItemsByList(itemIds);
    }
}
