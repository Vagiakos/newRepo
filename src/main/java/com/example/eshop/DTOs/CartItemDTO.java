package com.example.eshop.DTOs;

import com.example.eshop.Models.CartItem;

public class CartItemDTO {
    private Long cartItemId;
    private String brand;
    private double price;
    private int quantity;

    public CartItemDTO(CartItem item) {
        this.cartItemId = item.getId();
        this.brand = item.getProduct().getBrand();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
