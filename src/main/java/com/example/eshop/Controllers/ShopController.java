package com.example.eshop.Controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eshop.Models.Product;
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

    @DeleteMapping("/deleteProduct")
    public String deleteProduct(@RequestParam String brand) {
        productService.deleteShopProduct(brand);
        return "Product '" + brand + "' deleted successfully";
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