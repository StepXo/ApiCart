package com.emazon.ApiCart.Infrastructure.Adapters.Feign;

import com.emazon.ApiCart.Domain.Spi.StockFeignPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StockFeign implements StockFeignPort {
    private final IStockFeign stock;
    @Override
    public double checkStock(Long item,long quantity) {
        return  stock.checkStock(item,quantity).getPrice();
    }
}
