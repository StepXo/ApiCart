package com.emazon.ApiCart.Infrastructure.Adapters.Feign;

import com.emazon.ApiCart.Domain.Exeptions.ItemNotFoundException;
import com.emazon.ApiCart.Domain.Exeptions.QuantityIsNotEnough;
import com.emazon.ApiCart.Domain.Model.Item;
import com.emazon.ApiCart.Domain.Spi.StockFeignPort;
import com.emazon.ApiCart.Infrastructure.Persistance.Mapper.ItemMapper;
import feign.FeignException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class StockFeign implements StockFeignPort {
    private final IStockFeign stock;
    private final ItemMapper itemMapper;

    @Override
    public void checkStock(long item, long quantity) {

        try {
            stock.checkStock(item, quantity);
        } catch (FeignException.NotFound e) {
            throw new ItemNotFoundException();
        }
    }

    @Override
    public List<Item> getItemsByList(List<Long> list) {
        return Objects.requireNonNull(stock.getItemsByList(list).getBody()).stream()
                .map(itemMapper::toItem)
                .toList();
    }
}
