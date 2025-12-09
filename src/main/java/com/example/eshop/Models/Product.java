package com.example.eshop.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {

    @Id
    private Long code;
    private String type;
    private String discription;
    private double price;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "shop_afm")
    private Shop shop;

    public Product(Long code, String type, String discription, double price, int quantity) {
        this.code = code;
        this.type = type;
        this.discription = discription;
        this.price = price;
        this.quantity = quantity;
    }

    public Product() {
    }
}
