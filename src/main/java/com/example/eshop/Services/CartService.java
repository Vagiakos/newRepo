package com.example.eshop.Services;

import java.util.List;
import java.util.Optional;

import com.example.eshop.ErrorHandling.CartQuantityException;
import com.example.eshop.ErrorHandling.InternalServerException;
import com.example.eshop.ErrorHandling.NotFoundException;
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
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        Product product = productRepository.findById(brand)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getBrand().equals(brand))
                .filter(item -> !item.isCompleted())
                .findFirst()
                .orElse(null);

        int quantityitem = 0;
        if(cartItem != null){
            quantityitem = cartItem.getQuantity();
        }
        //check stock
        if(product.getQuantity() > quantityitem){

            if (cartItem != null) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            } else {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(cart);
                cartItem.setQuantity(quantity);
                cartItem.setPrice(product.getPrice());
                cart.addCartItem(cartItem);
            }

            cart.setTotalQuantity(cart.getTotalQuantity() + quantity);
            cart.setPrice(cart.getPrice() + product.getPrice() * quantity);

            cartRepository.save(cart);
            return "Product added";
        } else {
            //if requested quantity > stock
            throw new CartQuantityException("Requested quantity exceeds available stock. Only " + product.getQuantity() + " available");

        }
    }

    //it protects from race conditions (users press buy at the same time)
    //use transactional to rollback the updates (atomic changes)
    @Transactional
    public void buyProductsFromCart(Long cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if(!optionalCart.isPresent())
            throw new NotFoundException("Cart not found!");
        Cart cart = optionalCart.get();

        List<CartItem> items = cart.getCartItems();

        // check stock for each item
        for(CartItem item : items){
            Product p = item.getProduct();
            if(p.getQuantity() < item.getQuantity())
                throw new CartQuantityException(p.getBrand() + " out of stock");
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
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        if (cart.isCompleted()) {
            throw new InternalServerException("Completed cart");
        }

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getBrand().equals(brand))
                .filter(item -> !item.isCompleted())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Product not in cart"));

        if (quantity >= cartItem.getQuantity()) {
            cart.setTotalQuantity(cart.getTotalQuantity() - cartItem.getQuantity());
            cart.setPrice(cart.getPrice() - cartItem.getPrice() * cartItem.getQuantity());

            cart.getCartItems().remove(cartItem);

        } else {
            cartItem.setQuantity(cartItem.getQuantity() - quantity);
            cart.setTotalQuantity(cart.getTotalQuantity() - quantity);
            cart.setPrice(cart.getPrice() - cartItem.getPrice() * quantity);
        }

        cartRepository.save(cart);
    }
}