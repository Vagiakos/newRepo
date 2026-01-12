package com.example.eshop.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Shop extends User{

    private String brand;
    private String owner;


    @OneToMany(
        mappedBy = "shop",
        cascade = CascadeType.ALL,
        orphanRemoval = true //if product is removed from shop(list), delete it from db
    )

    //for bi-directional relationships
    //use to avoid infinite loop during serialization (shop -> products -> shop -> products ...)
    //it means that this side is the parent side (shop sends products in JSON)
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();

    public Shop() {
    }

    public Shop(Long afm, String username, String email, String brand, String owner, String password) {
        super(afm, username, email, password);
        this.brand = brand;
        this.owner = owner;

    }



    public String getOwner() {
        return owner;
    }

    public String getBrand() {
        return brand;
    }

    public List<Product> getProducts() {
        return products;
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


}
