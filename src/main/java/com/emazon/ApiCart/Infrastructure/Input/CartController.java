package com.emazon.ApiCart.Infrastructure.Input;

import com.emazon.ApiCart.Application.Response.ItemAuxDto;
import com.emazon.ApiCart.Application.Service.CartService;
import com.emazon.ApiCart.Application.Request.CartRequest;
import com.emazon.ApiCart.Application.Response.CartResponse;
import com.emazon.ApiCart.Infrastructure.Utils.InfraConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    private CartResponse addToCart(@RequestBody CartRequest request){
        return cartService.addToCart(request);
    }

    @PostMapping("/delete")
    private CartResponse deleteFromCart(@RequestBody CartRequest request){
        return cartService.deleteFromCart(request.getItemId());
    }

    @GetMapping("/list/{order}")
    private CartResponse getCartItems(
            @PathVariable String order,
            @RequestParam(defaultValue = InfraConstants.ZERO) int page,
            @RequestParam(defaultValue = InfraConstants.TEN) int size){
        return cartService.listAllCartItems(order, page, size);
    }

    @GetMapping("/list/{order}/{filter}/{name}")
    private CartResponse filterCartItems(
            @PathVariable String order,
            @PathVariable String filter,
            @PathVariable String name,
            @RequestParam(defaultValue = InfraConstants.ZERO) int page,
            @RequestParam(defaultValue = InfraConstants.TEN) int size){
        return cartService.listAllCartItems(order,filter,name, page, size);
    }


}
