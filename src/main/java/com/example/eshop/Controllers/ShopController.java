package com.example.eshop.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eshop.DTOs.LoginRequest;
import com.example.eshop.Services.ShopService;

@RestController
@RequestMapping("/shops")
public class ShopController {
    @Autowired
    private ShopService shopService;

    // /shops/login
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        //call shop service login method
        return shopService.login(
            request.getEmail(),
            request.getPassword()
        );
    }
}
