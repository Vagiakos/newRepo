package com.example.eshop.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.eshop.Models.Shop;
import com.example.eshop.Repositories.ShopRepository;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    // login method for shop
    public String login(String email, String password) {

        Optional<Shop> shopOpt = shopRepository.findByEmail(email);

        // check if shop exists
        if (shopOpt.isEmpty()) {
            //shop not found via email
            return "Shop not found";
        }

        Shop shop = shopOpt.get();

        // check password
        if (!shop.getPassword().equals(password)) {
            return "Wrong password";
        }

        // successful login
        return "Shop login successful";
    }

    //register shop 
    public void addShop(Shop shop) {
        
        // Validation email
        if (shop.getEmail() == null || !shop.getEmail().contains("@")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Email");
        }

        // Validation password
        if (shop.getPassword() == null || shop.getPassword().length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 characters");
        }

        // check if email already exists
        if (shopRepository.findByEmail(shop.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists!");
        }

        // save shop
        shopRepository.save(shop);
    }
}