package com.example.eshop.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
