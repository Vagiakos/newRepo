package com.example.eshop.Models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Shop {

    @Id
    private Long afm;
    private String brand;
    private String owner;
    private String password;
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List <Product> products;

    public Shop(Long afm, String brand, String owner, String password) {
        this.afm = afm;
        this.brand = brand;
        this.owner = owner;
        this.password = password;
        this.products = new ArrayList<Product>();
    }

    public Shop() {
    }

    public Long getAfm() {
        return afm;
    }

    public void setAfm(Long afm) {
        this.afm = afm;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    

}
