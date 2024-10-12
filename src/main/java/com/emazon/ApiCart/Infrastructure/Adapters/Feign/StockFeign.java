package com.emazon.ApiCart.Infrastructure.Adapters.Feign;

import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import com.emazon.ApiCart.Domain.Model.Item;
import com.emazon.ApiCart.Domain.Spi.StockFeignPort;
import com.emazon.ApiCart.Infrastructure.Persistance.Mapper.ItemMapper;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class StockFeign implements StockFeignPort {
    private final IStockFeign stock;
    private final ItemMapper itemMapper;

    @Override
    public void checkStock(long item, long quantity) {

        stock.checkStock(item,quantity);
    }

    @Override
    public List<Item> getItemsByList(List<Long> list) {
        return stock.getItemsByList(list).stream()
                .map(itemMapper::toItem)
                .toList();
    }
}
