package com.emazon.ApiCart.Application.Utils;

import com.emazon.ApiCart.Application.Response.CategoryDto;
import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import com.emazon.ApiCart.Application.Response.BrandDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FilterUtilTest {

    @InjectMocks
    private FilterUtil filterUtil;

    private List<ItemAuxDto> items;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        ItemAuxDto item1 = new ItemAuxDto();
        item1.setId(1L);
        item1.setName("item1");
        item1.setQuantity(2L);
        item1.setPrice(10.0);
        ItemAuxDto item2 = new ItemAuxDto();
        item2.setId(2L);
        item2.setName("item3");
        item2.setQuantity(3L);
        item2.setPrice(15.0);

        CategoryDto category1 = new CategoryDto(1L, "Electronics", "");
        item1.setCategory(List.of(category1));
        CategoryDto category2 = new CategoryDto(2L, "Clothing", "");
        item2.setCategory(List.of(category2));
        BrandDto brand1 = new BrandDto(1L, "Nike", "");
        item1.setBrand(brand1);
        BrandDto brand2 = new BrandDto(2L, "Adidas", "");
        item2.setBrand(brand2);

        items = List.of(item1, item2);

    }


    @Test
    void filterItems_ByCategory() {

        // Act
        List<ItemAuxDto> filteredItems = filterUtil.filterItems(items, "category", "Electronics");

        // Assert
        assertEquals(1, filteredItems.size());
        assertEquals("item1", filteredItems.get(0).getName());
    }


    @Test
    void filterItems_ByBrand() {

        // Act
        List<ItemAuxDto> filteredItems = filterUtil.filterItems(items, "brand", "Nike");

        // Assert
        assertEquals(1, filteredItems.size());
        assertEquals("item1", filteredItems.get(0).getName());
    }

    @Test
    void filterItems_NoMatches() {

        List<ItemAuxDto> filteredItems = filterUtil.filterItems(items, "brand", "Puma");

        assertTrue(filteredItems.isEmpty());
    }

    @Test
    void filterItems_InvalidFilter() {


        List<ItemAuxDto> filteredItems = filterUtil.filterItems(items, "unknownFilter", "Nike");

        assertTrue(filteredItems.isEmpty());
    }
}

