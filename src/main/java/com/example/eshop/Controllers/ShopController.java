package com.example.eshop.Controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.eshop.DTOs.LoginRequest;
import com.example.eshop.Models.Product;
import com.example.eshop.Models.Shop;
import com.example.eshop.Services.ProductService;
import com.example.eshop.Services.ShopService;

@RestController
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopService shopService;


    @GetMapping("/getProductsFromShop")
    public List<Product> getProductsFromShop(@RequestParam Long afm){
        return productService.getProductsFromShop(afm);
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @PutMapping("/update")
    public void UpdateShop(@RequestParam Long afm,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String brand,
                           @RequestParam(required = false) String owner,
                           @RequestParam(required = false) String password){
        shopService.updateShop(afm, username,email,brand, owner, password);
    }
}