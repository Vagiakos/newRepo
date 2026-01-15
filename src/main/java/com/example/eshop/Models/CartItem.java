package com.example.eshop.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity; // quantity of product in cart
    private double price; // price per brand when added
    private boolean isCompleted; // If the purchase is completed
    
    @Column(columnDefinition = "TIMESTAMP(0)")//cuts nano seconds
    private LocalDateTime addedAt; // timestamp addition

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart; // many CartItems can reference the same Cart(same cart_id)


    @ManyToOne
    @JoinColumn(name = "product_brand")
    private Product product; // many CartItems can reference the same Product(same brand)

    // constructor with timestamp
    public CartItem() {
        this.addedAt = LocalDateTime.now();
    }

    // full constructor CartItem
    public CartItem(int quantity, double price, boolean isCompleted, Cart cart, Product product) {
        this.quantity = quantity;
        this.price = price;
        this.isCompleted = isCompleted;
        this.cart = cart;
        this.product = product;
        this.addedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
