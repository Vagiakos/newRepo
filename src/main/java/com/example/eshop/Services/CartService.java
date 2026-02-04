package com.example.eshop.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eshop.ErrorHandling.CartQuantityException;
import com.example.eshop.ErrorHandling.InternalServerException;
import com.example.eshop.ErrorHandling.NotFoundException;
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

    @Transactional
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


        int quantity_helper = quantity;
        if (cartItem != null) {
            quantity_helper = quantity_helper + cartItem.getQuantity();
        }
        //check stock
        if(product.getQuantity() >= quantity_helper){

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

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        //check
        List<CartItem> items = cart.getCartItems();

        if (items.isEmpty()) {
            throw new InternalServerException("Cart is empty");
        }

        for (CartItem item : items) {
            if (item.isCompleted()) continue;
            Product product = productRepository.findById(
                    item.getProduct().getBrand()
            ).orElseThrow(() -> new NotFoundException("Product not found"));

            if (product.getQuantity() < item.getQuantity()) {
                throw new CartQuantityException(
                        product.getBrand() + " out of stock"
                );
            }

            product.setQuantity(
                    product.getQuantity() - item.getQuantity()
            );

            item.setCompleted(true);
        }
        cartRepository.save(cart);
    }

    @Transactional
    public void removeProductFromCart(Long cartId, String brand, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        //if (cart.isCompleted()) {
            //throw new InternalServerException("Completed cart");
        //}

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