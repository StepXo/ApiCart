package com.emazon.ApiCart.Infrastructure.Adapters.Feign;

import com.emazon.ApiCart.Domain.Exeptions.ItemNotFoundException;
import com.emazon.ApiCart.Domain.Exeptions.QuantityIsNotEnough;
import com.emazon.ApiCart.Domain.Model.Item;
import com.emazon.ApiCart.Domain.Spi.StockFeignPort;
import com.emazon.ApiCart.Infrastructure.Persistance.Mapper.ItemMapper;
import feign.FeignException;
import lombok.AllArgsConstructor;

import java.util.List;

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
        } catch (FeignException.InternalServerError | FeignException.BadRequest e) {
            throw new QuantityIsNotEnough();
        }
    }

    @Override
    public List<Item> getItemsByList(List<Long> list) {
        return stock.getItemsByList(list).stream()
                .map(itemMapper::toItem)
                .toList();
    }
}
