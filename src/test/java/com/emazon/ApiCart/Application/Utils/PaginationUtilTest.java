package com.emazon.ApiCart.Application.Utils;

import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaginationUtilTest {

    @InjectMocks
    private PaginationUtil paginationUtil;

    private List<ItemAuxDto> itemList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        itemList =  Arrays.asList(
                setItemAuxDto(1L,"Item1",10L,100.0),
                setItemAuxDto(2L,"Item2",20L,200.0),
                setItemAuxDto(3L,"Item3",30L,300.0),
                setItemAuxDto(4L,"Item4",40L,400.0)
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
    void getItemsPagination_AscOrder() {
        // Act
        Page<ItemAuxDto> paginatedItems = paginationUtil.getItemsPagination("asc", 0, 2, itemList);

        // Assert
        assertEquals(2, paginatedItems.getContent().size());
        assertEquals("Item1", paginatedItems.getContent().get(0).getName());
        assertEquals("Item2", paginatedItems.getContent().get(1).getName());
    }

    @Test
    void getItemsPagination_DescOrder() {

        itemList.sort((a, b) -> b.getName().compareTo(a.getName()));

        Page<ItemAuxDto> paginatedItems = paginationUtil.getItemsPagination("desc", 0, 2, itemList);

        // Assert
        assertEquals(2, paginatedItems.getContent().size());
        assertEquals("Item4", paginatedItems.getContent().get(0).getName());
        assertEquals("Item3", paginatedItems.getContent().get(1).getName());
    }

    @Test
    void getItemsPagination_PageOutOfBounds() {
        // Act
        Page<ItemAuxDto> paginatedItems = paginationUtil.getItemsPagination("asc", 5, 2, itemList);

        // Assert
        assertEquals(0, paginatedItems.getContent().size());
    }

    @Test
    void getItemsPagination_PartialPage() {
        // Act
        Page<ItemAuxDto> paginatedItems = paginationUtil.getItemsPagination("asc", 1, 3, itemList);

        // Assert
        assertEquals(1, paginatedItems.getContent().size());
        assertEquals("Item4", paginatedItems.getContent().get(0).getName());
    }


}