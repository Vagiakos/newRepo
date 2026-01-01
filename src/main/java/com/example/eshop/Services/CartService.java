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
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if(!optionalCart.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        Cart cart = optionalCart.get();

        Optional<Product> optionalProduct = productRepository.findById(brand);
        if(!optionalProduct.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        Product product = optionalProduct.get();

        //μπορει ο ελεγχος να γινεται και στο product απο αποψη σχεδιασης
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

    //εδω να ρωτησω αν πρεπει να κανω καποια σχετικη υλοποιηση αν πατησουν απο το front ταυτοχρονα το κουμπι buy δυο χρηστες
    //κανω την μεθοδο transactional ωστε αν κατι παει λαθος στο δευτερο loop να κανει roll back οτι αλλαγη εγινε πιο πριν
    @Transactional
    public void buyProductsFromCart(Long cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if(!optionalCart.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart Not found");
        Cart cart = optionalCart.get();
        List<Product> products = cart.getProducts();
        for(Product p : products){
            if(p.getQuantity() == 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, p.getBrand() + " out of stock");
        }

        for(Product p : products){
            p.setQuantity(p.getQuantity() - 1);
            productRepository.save(p);
        }

        cart.clearProducts();
        cart.setPrice(0);
        cartRepository.save(cart);

    }
}
