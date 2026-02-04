package com.example.eshop.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    private String brand;

    private String type;
    private String description;
    private double price;
    private int quantity;
    
    @ManyToOne
    @JoinColumn(name = "shop_afm")

    //other side of bi-directional relationship
    //product doesnt send shop in JSON (to avoid infinite loop)
    @JsonBackReference 
    private Shop shop;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartItems = new ArrayList<>();

    public Product() {
    }

    public Product(String brand, String type, String description, double price, int quantity) {
        this.brand = brand;
        this.type = type;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Shop getShop() {
        return shop;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}