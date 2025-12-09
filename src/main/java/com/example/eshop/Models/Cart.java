package com.example.eshop.Models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private double price;
    @OneToOne(mappedBy = "cart")
    private Citizen citizen;
    @OneToMany
    private List<Product> products;


    public Cart(double price) {
        this.price = price;
        this.products = new ArrayList<Product>();
    }

    public Cart() {
    }


}
