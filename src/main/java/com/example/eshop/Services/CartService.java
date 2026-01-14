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

    public String addProductToCart(Long cartId, String brand, int quantity) {
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
            
            //update cart price and quantity
            cart.setPrice(cart.getPrice() + product.getPrice() * quantity);
            cart.setTotalQuantity(cart.getTotalQuantity() + quantity);
            cartRepository.save(cart);

            return "Product added";
        }else {
            //if requested quantity > stock
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Requested quantity exceeds available stock. Only " + product.getQuantity() + " available");
        }
    }

    //it protects from race conditions (users press buy at the same time)
    //use transactional to rollback the updates (atomic changes)
    @Transactional
    public void buyProductsFromCart(Long cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if(!optionalCart.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart Not found");
        Cart cart = optionalCart.get();
        List<Product> products = cart.getProducts();
        //if out-of-stock → exception → rollback
        for(Product p : products){
            if(p.getQuantity() == 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, p.getBrand() + " out of stock");
        }

        //we can use Map<Product, Integer> to subtract exactly the amount the user entered
        for(Product p : products){
            p.setQuantity(p.getQuantity() - 1);
            productRepository.save(p);
        }

        
        cart.clearProducts();
        cart.setPrice(0);
        cart.setTotalQuantity(0);
        cart.setCompleted(true);
        cartRepository.save(cart);

    }

    public void removeProductFromCart(Long cartId, String brand, int quantity) {
    // find cart
    Cart cart = cartRepository.findById(cartId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

    // find product
    Product product = productRepository.findById(brand)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

    // remove from cart
    int removedCount = 0; //counter for removed items
    List<Product> products = cart.getProducts();
    for (int i = products.size() - 1; i >= 0 && removedCount < quantity; i--) { //LIFO
        if (products.get(i).getBrand().equals(brand)) {
            products.remove(i);
            removedCount++;
        }
    }

    if (removedCount > 0) {
        // refresh cart price (can't be negative)
        cart.setPrice(Math.max(cart.getPrice() - removedCount * product.getPrice(), 0));
        // refresh cart total quantity
        cart.setTotalQuantity(Math.max(cart.getTotalQuantity() - removedCount, 0));

        // if requested quantity > available in cart
        if (removedCount < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Requested quantity exceeds quantity in cart. Only " + removedCount + " removed");
        }

        cartRepository.save(cart);
        } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not in cart");
        }
    }
}