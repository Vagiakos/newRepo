package com.example.eshop.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Shop {

    @Id
    private Long afm;

    @Column(unique = true, nullable = false) //unique email and not null
    private String email;

    private String brand;
    private String owner;
    private String password;

    @OneToMany(
        mappedBy = "shop",
        cascade = CascadeType.ALL,
        orphanRemoval = true //if product is removed from shop(list), delete it from db
    )
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();

    public Shop() {
    }

    public Shop(Long afm, String email, String brand, String owner, String password) {
        this.afm = afm;
        this.email = email;
        this.brand = brand;
        this.owner = owner;
        this.password = password;
    }

    public Long getAfm() {
        return afm;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setAfm(Long afm) {
    this.afm = afm;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
