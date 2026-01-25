package com.example.eshop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.eshop.Models.Product;
import com.example.eshop.Services.CartService;
import com.example.eshop.Services.ProductService;

@CrossOrigin(origins = "*") // allow frontend
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @PostMapping("/addProductToCart")
    public String addProductToCart(@RequestParam  Long cartId,
                                @RequestParam String brand,
                                @RequestParam int quantity){
        return cartService.addProductToCart(cartId, brand, quantity);
    }

    @GetMapping("/getProduct")
    public Product getProduct(@RequestParam String brand){
        return productService.getProduct(brand);
    }

    //update quantity (shop)
    @PutMapping("/updateQuantity")
    public String updateProductQuantity(@RequestParam String brand,
                                        @RequestParam int quantity) {
        if(quantity < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity cannot be negative");
        }

        return productService.updateProductQuantity(brand, quantity);
    }

}