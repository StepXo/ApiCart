package com.emazon.ApiCart.Infrastructure.Adapters.Feign;

import com.emazon.ApiCart.Domain.Model.Item;
import com.emazon.ApiCart.Domain.Spi.StockFeignPort;
import com.emazon.ApiCart.Infrastructure.Persistance.Mapper.ItemMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StockFeign implements StockFeignPort {
    private final ItemMapper mapper;
    private final IStockFeign stock;
    @Override
    public Item getById(Item item) {
        return  mapper.toItem(stock.getById(mapper.toItemAuxDto(item)));
    }
}
