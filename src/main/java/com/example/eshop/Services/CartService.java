package com.example.eshop.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.eshop.Models.Cart;
import com.example.eshop.Models.Product;
import com.example.eshop.Repositories.CartRepository;
import com.example.eshop.Repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    public void addProductToCart(Long cartId, String brand, int quantity) {
        //search for cart
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if(!optionalCart.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        Cart cart = optionalCart.get();
    
        //search for citizen
        Optional<Product> optionalProduct = productRepository.findById(brand);
        if(!optionalProduct.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        Product product = optionalProduct.get();
        
        //check stock
        if(product.getQuantity() >= quantity){
            //add products in cart
            for(int i = 0; i < quantity; i++){
                cart.addProduct(product);
            }
            //update cart price
            cart.setPrice(cart.getPrice() + product.getPrice() * quantity);
            cartRepository.save(cart);
        }else {
            //if requested quantity > stock
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Requested quantity exceeds available stock. Only " + product.getQuantity() + " available");
        }
    }


}