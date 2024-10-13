package com.emazon.ApiCart.Infrastructure.Adapters.Feign;

import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import com.emazon.ApiCart.Infrastructure.Configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "Api-Stock", url = "${stock.api.url}",configuration = FeignConfiguration.class)
public interface IStockFeign  {


    @GetMapping( value = "/cart/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ItemAuxDto checkStock(@PathVariable long id, @RequestParam long quantity);

    @GetMapping( value = "/cart/list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    List<ItemAuxDto> getItemsByList( @RequestParam List<Long> list);
}
