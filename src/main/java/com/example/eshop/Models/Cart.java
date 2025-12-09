package com.example.eshop.Models;

import jakarta.persistence.*;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private double price;
    @OneToOne(mappedBy = "cart")
    private Citizen citizen;
    private Product product;

    public Cart(double price) {
        this.price = price;
    }

    public Cart() {
    }


}
