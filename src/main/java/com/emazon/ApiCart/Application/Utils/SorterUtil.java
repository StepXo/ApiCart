package com.emazon.ApiCart.Application.Utils;

import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static com.emazon.ApiCart.Application.Utils.AppConstants.ORDER;

@Service
@RequiredArgsConstructor
@Transactional
public class SorterUtil {
    public List<ItemAuxDto> orderItems(List<ItemAuxDto> items, String order){
        Comparator<ItemAuxDto> comparator = ORDER.equalsIgnoreCase(order)
                ? Comparator.comparing(ItemAuxDto::getName)
                : Comparator.comparing(ItemAuxDto::getName).reversed();

        return items.stream()
                .sorted(comparator)
                .toList();
    }
}
