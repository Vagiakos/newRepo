package com.example.eshop.Models;

import java.util.ArrayList;
import java.util.List;

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
}
