package com.example.eshop.Models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

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
    private Shop shop;

    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;

    public Product() {
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
}