package com.example.eshop.DTOs;


public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private String typeOfUser;

    //Shop
    private Long afmShop;
    private String brandShop;
    private String owner;

    //Citizen
    private Long afmCitizen;
    private String name;
    private String surname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public Long getAfmShop() {
        return afmShop;
    }

    public void setAfmShop(Long afmShop) {
        this.afmShop = afmShop;
    }

    public String getBrandShop() {
        return brandShop;
    }

    public void setBrandShop(String brandShop) {
        this.brandShop = brandShop;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getAfmCitizen() {
        return afmCitizen;
    }

    public void setAfmCitizen(Long afmCitizen) {
        this.afmCitizen = afmCitizen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
