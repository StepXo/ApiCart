package com.emazon.ApiCart.Domain.Spi;


public interface StockFeignPort {
    Long checkStock(Long itemId,long quantity);
}
