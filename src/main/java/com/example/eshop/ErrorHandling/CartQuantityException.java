package com.example.eshop.ErrorHandling;

public class CartQuantityException extends RuntimeException {

    public CartQuantityException() {
    }

    public CartQuantityException(String message) {
        super(message);
    }
}
