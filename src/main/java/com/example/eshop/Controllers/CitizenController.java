package com.example.eshop.Controllers;

import com.example.eshop.Models.Citizen;
import com.example.eshop.Models.Product;
import com.example.eshop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.eshop.DTOs.LoginRequest;
import com.example.eshop.Services.CitizenService;

import java.util.List;

@RestController
@RequestMapping("/citizens")
public class CitizenController {
    
    @Autowired
    private CitizenService citizenService;

    @Autowired
    private ProductService productService;

    // /citizens/login
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) { //request body contains email and password
        //call citizen service login method
        return citizenService.login(
            //use getters from dto
            request.getEmail(),
            request.getPassword()
        );
    }

    @PostMapping("/registerCitizen")
    public void registerCitizen(@RequestBody Citizen citizen) {
        citizenService.addCitizen(citizen);
    }

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }


    //ΕΚΚΡΕΜΟΤΗΤΑ
    //να κανω και αναζητηση με βαση το shop
    @GetMapping("/getProductsByFilters")
    public List<Product> getProductsByFilters(@RequestParam(required = false) String brand,
                                              @RequestParam(required = false) String type,
                                              @RequestParam(required = false) Double price){
        return productService.getProductsByFilters(brand, type,price);


    }
}
