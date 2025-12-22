package com.example.eshop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eshop.DTOs.LoginRequest;
import com.example.eshop.Services.CitizenService;

@RestController
@RequestMapping("/citizens")
public class CitizenController {
    
    @Autowired
    private CitizenService citizenService;

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
}
