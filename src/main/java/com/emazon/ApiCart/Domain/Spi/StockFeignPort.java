package com.emazon.ApiCart.Domain.Spi;


public interface StockFeignPort {
    double checkStock(Long itemId,long quantity);
}
