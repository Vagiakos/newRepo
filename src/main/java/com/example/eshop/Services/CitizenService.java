package com.example.eshop.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.eshop.Models.Cart;
import com.example.eshop.Models.CartItem;
import com.example.eshop.Models.Citizen;
import com.example.eshop.Models.Product;
import com.example.eshop.Repositories.CartRepository;
import com.example.eshop.Repositories.CitizenRepository;
import com.example.eshop.Repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class CitizenService {
    
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    //it protects from race conditions (users press buy at the same time)
    //use transactional to rollback the updates (atomic changes)
    @Transactional
    public void buyProductsFromCart(Long cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if(!optionalCart.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart Not found");
        Cart cart = optionalCart.get();

        List<CartItem> items = cart.getCartItems();

        //if out-of-stock → exception → rollback
        for(CartItem item : items){
            Product p = item.getProduct();
            if(p.getQuantity() < item.getQuantity())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, p.getBrand() + " out of stock");
        }

        //subtract stock and mark CartItem completed
        for(CartItem item : items){
            Product p = item.getProduct();
            p.setQuantity(p.getQuantity() - item.getQuantity());
            productRepository.save(p);

            item.setCompleted(true);
        }

        //mark cart completed
        cart.setCompleted(true);
        cartRepository.save(cart);
    }

    public void updateCitizen(Long afm,String username,String email,String name,String surname,String password){
        Optional<Citizen> optionalCitizen = citizenRepository.findById(afm);
        if(!optionalCitizen.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found!");
        Citizen citizen = optionalCitizen.get();

        if(username!=null)
            citizen.setUsername(username);
        if(email!=null) {
            if (email.contains("@"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Email");
            citizen.setEmail(email);
        }
        // check if email already exists
        if (citizenRepository.findByEmail(email).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists!");
        }
        if(name!=null)
            citizen.setName(name);
        if(surname!=null)
            citizen.setSurname(surname);

        if (password != null){
            if(password.length() < 6)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be 6 character or more");
            citizen.setPassword(password);
        }

        citizenRepository.save(citizen);
    }
}
