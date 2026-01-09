package com.example.eshop.Controllers;

import com.example.eshop.DTOs.LoginRequest;
import com.example.eshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) { //request body contains email and password
        //call citizen service login method
        return userService.login(
                //use getters from dto
                request.getType(),
                request.getEmail(),
                request.getPassword()
        );
    }
}
