package com.emazon.ApiCart.Application.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemAuxDto {
    private long id;
    private long quantity;
    private double price;


}

