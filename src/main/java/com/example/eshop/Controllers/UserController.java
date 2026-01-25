package com.example.eshop.Controllers;

import com.example.eshop.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eshop.DTOs.LoginRequest;
import com.example.eshop.DTOs.RegisterRequest;
import com.example.eshop.Services.UserService;

@CrossOrigin(origins = "*") // allow frontend
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) { //request body contains email and password
        //call user service login method
        return userService.login(
                //use getters from dto
                request.getEmail(),
                request.getPassword()
        );
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest registerRequest){
        userService.register(registerRequest);
    }
}
