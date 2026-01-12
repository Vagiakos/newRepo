package com.example.eshop.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Citizen extends User{
   
    private String name;
    private String surname;

    @OneToOne(cascade = CascadeType.ALL) //save, delete, update, merge, refresh
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public Citizen(Long afm, String username, String name, String surname, String email, String password) {
        super(afm, username, email, password);
        this.name = name;
        this.surname = surname;

    }

    public Citizen() {
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
