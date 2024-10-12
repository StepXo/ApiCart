package com.emazon.ApiCart.Domain.Usecase;

import com.emazon.ApiCart.Domain.Api.ItemServicePort;
import com.emazon.ApiCart.Domain.Model.Item;
import com.emazon.ApiCart.Domain.Spi.StockFeignPort;

import java.util.List;

public class ItemUseCase implements ItemServicePort {

    private final StockFeignPort stockFeignPort;

    public ItemUseCase( StockFeignPort stockFeignPort) {
        this.stockFeignPort = stockFeignPort;
    }

    @Override
    public List<Item> getItemList(List<Long> items) {
        return stockFeignPort.getItemsByList(items);
    }
}
