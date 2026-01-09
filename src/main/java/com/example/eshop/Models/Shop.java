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

    @Id
    private Long afm;

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

    public Shop(String username, Long afm, String email, String brand, String owner, String password) {
        super(username, email, password);
        this.afm = afm;
        this.brand = brand;
        this.owner = owner;

    }

    public Long getAfm() {
        return afm;
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

    public void setAfm(Long afm) {
    this.afm = afm;
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


}
