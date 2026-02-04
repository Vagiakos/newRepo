package com.example.eshop.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double price;

    private int totalQuantity; //total number of products
    
    // @Column(nullable = false)
    // private boolean completed = false;

    @OneToOne(mappedBy = "cart")
    private Citizen citizen;

    // @ManyToMany
    // @JoinTable(
    //     name = "cart_products",
    //     joinColumns = @JoinColumn(name = "cart_id"),
    //     inverseJoinColumns = @JoinColumn(name = "product")
    // )

    //private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartItems = new ArrayList<>();

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addCartItem(CartItem item) {
    cartItems.add(item);
        item.setCart(this); 
    }

    public void removeCartItem(CartItem item) {
        cartItems.remove(item);
        item.setCart(null);
    }
    
    public Cart() { 
    }

    public Long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // public List<Product> getProducts() {
    //     return products;
    // }

    // public void clearProducts(){
    //     products.clear();
    // }

    // public void addProduct(Product product) {
    //     this.products.add(product);
    // }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    //public boolean isCompleted() {
        //return completed;
    //}

    //public void setCompleted(boolean completed) {
        //this.completed = completed;
    //}
}