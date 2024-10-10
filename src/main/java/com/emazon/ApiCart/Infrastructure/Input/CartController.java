package com.emazon.ApiCart.Infrastructure.Input;

import com.emazon.ApiCart.Application.Handler.CartHandler;
import com.emazon.ApiCart.Application.Service.CartService;
import com.emazon.ApiCart.Application.Request.CartRequest;
import com.emazon.ApiCart.Application.Response.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    private List<CartResponse> getCartItems(){
        return cartService.listAllCartItems();
    }

    @GetMapping("/{filter}")
    private List<CartResponse> filterCartItems(@PathVariable String filter){
        return cartService.listAllCartItems(filter);
    }


}
