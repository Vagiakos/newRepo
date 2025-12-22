package com.example.eshop.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
