package com.emazon.ApiCart.Application.Utils;

import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SorterUtilTest {

    @InjectMocks
    private SorterUtil sorterUtil;

    private List<ItemAuxDto> itemList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemList = new ArrayList<>();
        itemList.add(this.setItemAuxDto(3L,"Item3"));
        itemList.add(this.setItemAuxDto(1L,"Item1"));
        itemList.add(this.setItemAuxDto(4L,"Item4"));
        itemList.add(this.setItemAuxDto(2L,"Item2"));
    }

    private ItemAuxDto setItemAuxDto(long index,String name){
        ItemAuxDto auxDto = new ItemAuxDto();
        auxDto.setId(index);
        auxDto.setName(name);
        return auxDto;
    }

    @Test
    void orderItems_AscOrder() {
        List<ItemAuxDto> sortedItems = sorterUtil.orderItems(itemList, "asc");

        assertEquals("Item1", sortedItems.get(0).getName());
        assertEquals("Item2", sortedItems.get(1).getName());
        assertEquals("Item3", sortedItems.get(2).getName());
        assertEquals("Item4", sortedItems.get(3).getName());
    }

    @Test
    void orderItems_DescOrder() {
        List<ItemAuxDto> sortedItems = sorterUtil.orderItems(itemList, "desc");

        assertEquals("Item4", sortedItems.get(0).getName());
        assertEquals("Item3", sortedItems.get(1).getName());
        assertEquals("Item2", sortedItems.get(2).getName());
        assertEquals("Item1", sortedItems.get(3).getName());
    }

    @Test
    void orderItems_EmptyList() {
        List<ItemAuxDto> sortedItems = sorterUtil.orderItems(new ArrayList<>(), "asc");
        assertEquals(0, sortedItems.size());
    }
}
