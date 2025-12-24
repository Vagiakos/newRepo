package com.example.eshop.Controllers;
import com.example.eshop.Models.Product;
import com.example.eshop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.eshop.DTOs.LoginRequest;
import com.example.eshop.Services.ShopService;

import java.util.List;

@RestController
@RequestMapping("/shops")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    // /shops/login
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        //call shop service login method
        return shopService.login(
            request.getEmail(),
            request.getPassword()
        );
    }

    @GetMapping("/getProductsFromShop")
    public List<Product> getProductsFromShop(@RequestParam Long afm){
        return productService.getProductsFromShop(afm);
    }
}
