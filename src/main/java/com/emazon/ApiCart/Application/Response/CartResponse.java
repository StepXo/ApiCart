package com.emazon.ApiCart.Application.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private long id;
    private long userId;
    private Page<ItemAuxDto> item;
    private double total;
    private String actualizationDate;
    private String creationDate;
}
