package com.example.eshop.Controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eshop.DTOs.LoginRequest;
import com.example.eshop.Models.Product;
import com.example.eshop.Models.Shop;
import com.example.eshop.Services.ProductService;
import com.example.eshop.Services.ShopService;

@RestController
@RequestMapping("/shops")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;


    @GetMapping("/getProductsFromShop")
    public List<Product> getProductsFromShop(@RequestParam Long afm){
        return productService.getProductsFromShop(afm);
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }  
}