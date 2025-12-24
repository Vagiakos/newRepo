package com.example.eshop.Controllers;

import com.example.eshop.Models.Product;
import com.example.eshop.Services.CartService;
import com.example.eshop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;


    @PostMapping("/addProductToCart")
        public void addProductToCart(Long cartId, String brand){
            cartService.addProductToCart(cartId, brand);
        }


    @GetMapping("/getProduct")
    public Product getProduct(String brand){
        return productService.getProduct(brand);
    }


}
