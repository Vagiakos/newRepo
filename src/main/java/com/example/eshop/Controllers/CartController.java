package com.example.eshop.Controllers;
import com.example.eshop.Models.Cart;
import com.example.eshop.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/buyProductsFromCart")
    public void buyProductsFromCart(@RequestParam Long cartId){
        cartService.buyProductsFromCart(cartId);
    }



}
