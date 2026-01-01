package com.example.eshop.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eshop.Models.Cart;
import com.example.eshop.Models.Citizen;
import com.example.eshop.Repositories.CitizenRepository;

@Service
public class CitizenService {
    
    @Autowired
    private CitizenRepository citizenRepository; 

    //login method for citizen
    public String login(String email, String password) {
        
        Optional<Citizen> citizenOpt = citizenRepository.findByEmail(email);
        //check if citizen exists
        if (citizenOpt.isEmpty()) {
            //citizen not found via email
            return "Citizen not found";
        }
        //validate password
        Citizen citizen = citizenOpt.get();
        //check password
        if (!citizen.getPassword().equals(password)) {
            //wrong password
            return "Wrong password";
        }
        //successful login
        return "Citizen login successful";
    }

    public void addCitizen(Citizen citizen) {
    
    // Validation email 
    if (citizen.getEmail() == null || !citizen.getEmail().contains("@")) {
        throw new IllegalArgumentException("Invalid email");
    }

    // Validation password
    if (citizen.getPassword() == null || citizen.getPassword().length() < 6) {
        throw new IllegalArgumentException("Password must be at least 6 characters");
    }
    
    // Validation name and surname
    if (citizen.getName() == null || citizen.getName().isBlank() ||
        citizen.getSurname() == null || citizen.getSurname().isBlank()) {
        throw new IllegalArgumentException("Name and surname cannot be empty");
    }

    // check if email already exists
    if (citizenRepository.findByEmail(citizen.getEmail()).isPresent()) {
        throw new IllegalArgumentException("Email already exists");
    }

    // create cart automatically when a citizen is registered
    Cart cart = new Cart();
    citizen.setCart(cart); 
    citizenRepository.save(citizen); // save citizen with cart (cascade)
    }

}