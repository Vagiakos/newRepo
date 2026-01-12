package com.example.eshop.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.eshop.DTOs.LoginRequest;
import com.example.eshop.Models.Citizen;
import com.example.eshop.Models.Product;
import com.example.eshop.Services.CitizenService;
import com.example.eshop.Services.ProductService;

@RestController
@RequestMapping("/citizens") 
public class CitizenController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CitizenService citizenService;

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }


    @GetMapping("/getProductsByFilters")
    public List<Product> getProductsByFilters(@RequestParam(required = false) String brand, //can be null
                                              @RequestParam(required = false) String type,
                                              @RequestParam(required = false) Double priceMin,
                                              @RequestParam(required = false) Double priceMax,
                                              @RequestParam(required = false) Long shop_afm){
        return productService.getProductsByFilters(brand, type, priceMin,priceMax, shop_afm);
    }

    @PutMapping("/update")
    public void updateCitizen(@RequestParam Long afm,
                              @RequestParam(required = false) String username,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String surname,
                              @RequestParam(required = false) String password){
        citizenService.updateCitizen(afm, username,email,name, surname, password);
    }




}