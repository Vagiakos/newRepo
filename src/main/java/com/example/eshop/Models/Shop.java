package com.example.eshop.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Shop {

    @Id
    private Long afm;
    private String brand;
    private String owner;
    private String password;
    private Product product;

    public Shop(Long afm, String brand, String owner, String password) {
        this.afm = afm;
        this.brand = brand;
        this.owner = owner;
        this.password = password;
    }

    public Shop() {
    }


}
