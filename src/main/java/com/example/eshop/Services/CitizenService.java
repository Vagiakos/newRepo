package com.example.eshop.Services;

import java.util.List;
import java.util.Optional;

import com.example.eshop.Models.Product;
import com.example.eshop.Repositories.CartRepository;
import com.example.eshop.Repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.eshop.Models.Cart;
import com.example.eshop.Models.Citizen;
import com.example.eshop.Repositories.CitizenRepository;

@Service
public class CitizenService {
    
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

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
        cartRepository.save(cart);

    }

//    public void addCitizen(Citizen citizen) {
//
//    // Validation email
//    if (citizen.getEmail() == null || !citizen.getEmail().contains("@")) {
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Email");
//    }
//
//    // Validation password
//    if (citizen.getPassword() == null || citizen.getPassword().length() < 6) {
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 characters");
//    }
//
//    // Validation name and surname
//    if (citizen.getName() == null || citizen.getName().isBlank() ||
//        citizen.getSurname() == null || citizen.getSurname().isBlank()) {
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name and surname cannot be empty");
//    }
//
//    // check if email already exists
//    if (citizenRepository.findByEmail(citizen.getEmail()).isPresent()) {
//        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists!");
//    }
//
//    // create cart automatically when a citizen is registered
//    Cart cart = new Cart();
//    citizen.setCart(cart);
//    citizenRepository.save(citizen); // save citizen with cart (cascade)
//    }
}