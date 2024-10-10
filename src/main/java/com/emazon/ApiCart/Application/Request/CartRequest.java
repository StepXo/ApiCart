package com.emazon.ApiCart.Application.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {
    private long id;
    private long itemId;
    private int quantity;
}
