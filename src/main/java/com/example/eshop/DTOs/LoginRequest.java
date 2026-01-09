package com.example.eshop.DTOs;

// dto for login requests from citizens and shops
public class LoginRequest {

    private String email; // login via email and password
    private String password;
    private String type;

    public LoginRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}