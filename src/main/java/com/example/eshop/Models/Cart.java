package com.example.eshop.Models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="products_carts",
        joinColumns = @JoinColumn(name="product_name"), 
        inverseJoinColumns = @JoinColumn(name="cart_name"))

    private List<Product> products;

    public Cart(double price) {
        this.price = price;
        this.products = new ArrayList<Product>();
    }

    public Cart() {
    }

}
