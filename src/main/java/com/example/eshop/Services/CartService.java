package com.example.eshop.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.eshop.Models.Cart;
import com.example.eshop.Models.CartItem;
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
    
        //search for product
        Optional<Product> optionalProduct = productRepository.findById(brand);
        if(!optionalProduct.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        Product product = optionalProduct.get();
        
        //check stock
        if(product.getQuantity() >= quantity){
            
            //create a single CartItem with the requested quantity
            double totalPrice = Math.round(product.getPrice() * quantity * 100.0) / 100.0;
            CartItem item = new CartItem(quantity, totalPrice, false, cart, product);
            cart.addCartItem(item);

            //update cart price and quantity
            cart.setPrice(cart.getPrice() + product.getPrice() * quantity);
            cart.setTotalQuantity(cart.getTotalQuantity() + quantity);

            cartRepository.save(cart);

            return "Product added";
        } else {
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

        List<CartItem> items = cart.getCartItems();

        // check stock for each item
        for(CartItem item : items){
            Product p = item.getProduct();
            if(p.getQuantity() < item.getQuantity())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, p.getBrand() + " out of stock");
        }

        // subtract stock
        for(CartItem item : items){
            Product p = item.getProduct();
            p.setQuantity(p.getQuantity() - item.getQuantity());
            productRepository.save(p);

            // mark item as completed
            if(item.getQuantity() > 0) {
                item.setCompleted(true);
            }
        }

        // mark cart as completed
        cart.setCompleted(true);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(Long cartId, String brand, int quantity) {
        // find cart
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        // find CartItems with this product
        List<CartItem> items = cart.getCartItems();
        int removedCount = 0;

        for(int i = items.size() - 1; i >= 0 && removedCount < quantity; i--) { //LIFO
            CartItem item = items.get(i);
            if(item.getProduct().getBrand().equals(brand) && item.getQuantity() > 0){
                if(item.getQuantity() <= (quantity - removedCount)){
                    // remove amount but keep the CartItem
                    removedCount += item.getQuantity();
                    cart.setPrice(cart.getPrice() - item.getPrice());
                    cart.setTotalQuantity(cart.getTotalQuantity() - item.getQuantity());
                    item.setQuantity(0); // quantity = 0
                } else {
                    //remove only part of quantity
                    int reduceAmount = quantity - removedCount;
                    double unitPrice = item.getPrice() / item.getQuantity(); //price per product
                    item.setQuantity(item.getQuantity() - reduceAmount);
                    item.setPrice(unitPrice * item.getQuantity());
                    cart.setPrice(cart.getPrice() - unitPrice * reduceAmount);
                    cart.setTotalQuantity(cart.getTotalQuantity() - reduceAmount);
                    removedCount += reduceAmount;
                }
            }
        }

        if(removedCount > 0){
            cartRepository.save(cart);

            if(removedCount < quantity){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Requested quantity exceeds quantity in cart. Only " + removedCount + " removed");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not in cart");
        }
    }
}