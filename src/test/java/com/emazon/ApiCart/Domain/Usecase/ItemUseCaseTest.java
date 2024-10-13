package com.emazon.ApiCart.Domain.Usecase;

import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import com.emazon.ApiCart.Domain.Model.Item;
import com.emazon.ApiCart.Domain.Spi.StockFeignPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ItemUseCaseTest {

    @Mock
    private StockFeignPort stockFeignPort;

    @InjectMocks
    private ItemUseCase itemUseCase;

    private List<Long> itemIds;
    private List<Item> itemList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
        itemIds = new ArrayList<>();
        itemIds.add(1L);
        itemIds.add(2L);
        itemIds.add(3L);

        itemList = Arrays.asList(
                setItem(1L,"Item1",10L,100.0),
                setItem(2L,"Item2",5L,50.0),
                setItem(3L,"Item3",3L,20.0)
        );
    }
    private Item setItem(long index, String name, long quantity, double price){
        Item item = new Item();
        item.setId(index);
        item.setName(name);
        item.setQuantity(quantity);
        item.setPrice(price);
        return item;
    }

    @Test
    void getItemList_returnsCorrectItemList() {
        when(stockFeignPort.getItemsByList(itemIds)).thenReturn(itemList);

        List<Item> result = itemUseCase.getItemList(itemIds);

        verify(stockFeignPort, times(1)).getItemsByList(itemIds);

        assertEquals(3, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Item1", result.get(0).getName());
        assertEquals(10, result.get(0).getQuantity());
        assertEquals(100.0, result.get(0).getPrice());
    }

    @Test
    void getItemList_returnsEmptyListWhenNoItemsFound() {
        when(stockFeignPort.getItemsByList(itemIds)).thenReturn(new ArrayList<>());

        List<Item> result = itemUseCase.getItemList(itemIds);

        verify(stockFeignPort, times(1)).getItemsByList(itemIds);
        assertEquals(0, result.size());
    }

    @Test
    void getItemList_handlesNullResponse() {
        when(stockFeignPort.getItemsByList(itemIds)).thenReturn(null);

        List<Item> result = itemUseCase.getItemList(itemIds);

        verify(stockFeignPort, times(1)).getItemsByList(itemIds);
        assertEquals(null, result);
    }
}
