package com.example.eshop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eshop.Models.Product;
import com.example.eshop.Services.CartService;
import com.example.eshop.Services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;


    @PostMapping("/addProductToCart")
    public void addProductToCart(@RequestParam  Long cartId,
                                @RequestParam String brand,
                                @RequestParam int quantity){
        cartService.addProductToCart(cartId, brand, quantity);
    }

    @GetMapping("/getProduct")
    public Product getProduct(@RequestParam String brand){
        return productService.getProduct(brand);
    }
}