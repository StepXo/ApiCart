package com.emazon.ApiCart.Application.Utils;

import com.emazon.ApiCart.Application.Response.CategoryDto;
import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FilterUtil {
    public List<ItemAuxDto> filterItems(List<ItemAuxDto> items, String filter, String name) {

        List<ItemAuxDto> filteredItems = new ArrayList<>();

        for (ItemAuxDto item : items) {

            if ("category".equalsIgnoreCase(filter)) {
                for (CategoryDto category : item.getCategory()) {
                    if (category.getName().equalsIgnoreCase(name)) {
                        filteredItems.add(item);
                        break;
                    }
                }

            } else if ("brand".equalsIgnoreCase(filter) && item.getBrand().getName().equalsIgnoreCase(name)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }
}
