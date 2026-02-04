package com.example.eshop.Controllers;
import com.example.eshop.DTOs.CartItemDTO;
import com.example.eshop.Models.CartItem;
import com.example.eshop.Models.Product;
import com.example.eshop.Services.CitizenService;
import com.example.eshop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.eshop.Services.CartService;

import java.util.List;

@CrossOrigin(origins = "*") // allow frontend
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @PostMapping("/buyProductsFromCart")
    public void buyProductsFromCart(@RequestParam Long cartId){
        citizenService.buyProductsFromCart(cartId);
    }

    @DeleteMapping("/removeProductFromCart")
    public void removeProductFromCart(@RequestParam Long cartId, @RequestParam String brand, @RequestParam int quantity){
        cartService.removeProductFromCart(cartId, brand, quantity);
    }

    @GetMapping("/getCartProducts")
    public List<CartItemDTO> getCartProducts(@RequestParam Long cartId){
            return productService.getCartProducts(cartId);
    }

    @PostMapping("/addProductToCart")
    public String addProductToCart(@RequestParam  Long cartId,
                                   @RequestParam String brand,
                                   @RequestParam int quantity){
        return cartService.addProductToCart(cartId, brand, quantity);
    }
}