package com.emazon.ApiCart.Application.Utils;

import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaginationUtil {

    public Page<ItemAuxDto> getItemsPagination(String order, int page, int size, List<ItemAuxDto> sortedItemDto) {
        Sort sort = AppConstants.ORDER.equalsIgnoreCase(order)
                ? Sort.by(AppConstants.NAME).ascending()
                : Sort.by(AppConstants.NAME).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        int start = (int) pageable.getOffset();
        if (start >= sortedItemDto.size()) {
            return new PageImpl<>(List.of(), pageable, sortedItemDto.size());
        }

        int end = Math.min(start + pageable.getPageSize(), sortedItemDto.size());

        return new PageImpl<>(sortedItemDto.subList(start, end), pageable, sortedItemDto.size());
    }
}