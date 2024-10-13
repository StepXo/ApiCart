package com.emazon.ApiCart.Infrastructure.Input;

import com.emazon.ApiCart.Application.Service.CartService;
import com.emazon.ApiCart.Application.Request.CartRequest;
import com.emazon.ApiCart.Application.Response.CartResponse;
import com.emazon.ApiCart.Infrastructure.Utils.InfraConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(InfraConstants.CART)
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    private CartResponse addToCart(@RequestBody CartRequest request){
        return cartService.addToCart(request);
    }

    @PostMapping(InfraConstants.DELETE)
    private ResponseEntity<CartResponse> deleteFromCart(@RequestBody CartRequest request){
        return ResponseEntity.ok(cartService.deleteFromCart(request.getItemId()));
    }

    @GetMapping(InfraConstants.ORDER)
    private ResponseEntity<CartResponse> getCart(
            @PathVariable String order,
            @RequestParam(defaultValue = InfraConstants.ZERO) int page,
            @RequestParam(defaultValue = InfraConstants.TEN) int size){
        return ResponseEntity.ok(cartService.listAllCart(order, page, size));
    }

    @GetMapping(InfraConstants.TYPE_ORDER)
    private ResponseEntity<CartResponse> filterCart(
            @PathVariable String order,
            @PathVariable String filter,
            @RequestParam String name,
            @RequestParam(defaultValue = InfraConstants.ZERO) int page,
            @RequestParam(defaultValue = InfraConstants.TEN) int size){
        return ResponseEntity.ok(cartService.listAllCart(order,filter,name, page, size));
    }
    @PostMapping(InfraConstants.BUY)
    private ResponseEntity<CartResponse> buy(){
        return ResponseEntity.ok(cartService.buy());
    }


}
