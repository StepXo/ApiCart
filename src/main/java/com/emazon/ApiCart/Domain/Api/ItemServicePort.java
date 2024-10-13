package com.emazon.ApiCart.Domain.Api;

import com.emazon.ApiCart.Domain.Model.Item;

import java.util.List;

public interface ItemServicePort {
    List<Item> getItemList(List<Long> items);

}
