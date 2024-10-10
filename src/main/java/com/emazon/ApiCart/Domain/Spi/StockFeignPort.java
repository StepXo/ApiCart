package com.emazon.ApiCart.Domain.Spi;


import com.emazon.ApiCart.Domain.Model.Item;

public interface StockFeignPort {
    Item getById(Item itemId);
}
