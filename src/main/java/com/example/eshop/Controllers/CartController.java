package com.example.eshop.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eshop.Services.CartService;


@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/buyProductsFromCart")
    public void buyProductsFromCart(@RequestParam Long cartId){
        cartService.buyProductsFromCart(cartId);
    }
    
}
