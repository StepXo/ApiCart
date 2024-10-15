package com.emazon.ApiCart.Infrastructure.Adapters.Feign;

import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import com.emazon.ApiCart.Infrastructure.Configuration.FeignConfiguration;
import com.emazon.ApiCart.Infrastructure.Utils.InfraConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = InfraConstants.STOCK_API, url = "${stock.api.url}",configuration = FeignConfiguration.class)
public interface IStockFeign  {


    @GetMapping( value = InfraConstants.CHECK, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    void checkStock(@PathVariable long id, @RequestParam long quantity);

    @GetMapping( value = InfraConstants.CART, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<ItemAuxDto>> getItemsByList(@RequestParam List<Long> list);
}
