package com.emazon.ApiCart.Application.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private long id;
    private long itemId;
    private long userId;
    private int quantity;
    private String actualizationDate;
    private String creationDate;
}
