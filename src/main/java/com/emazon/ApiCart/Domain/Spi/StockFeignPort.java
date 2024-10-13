package com.emazon.ApiCart.Domain.Spi;


import com.emazon.ApiCart.Domain.Model.Item;

import java.util.List;

public interface StockFeignPort {
    void checkStock(long itemId, long quantity);
    List<Item> getItemsByList(List<Long> list);
}
