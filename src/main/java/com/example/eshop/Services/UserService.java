package com.example.eshop.Services;

import com.example.eshop.Models.Citizen;
import com.example.eshop.Models.Shop;
import com.example.eshop.Repositories.CitizenRepository;
import com.example.eshop.Repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class UserService {

    @Autowired
    CitizenRepository citizenRepository;

    @Autowired
    ShopRepository shopRepository;

    //login method
    public String login(String email, String password, String type) {

        if (type.equals("Citizen")) {
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
        } else if (type.equals("Shop")) {
            Optional<Shop> shopOpt = shopRepository.findByEmail(email);

            // check if shop exists
            if (shopOpt.isEmpty()) {
                //shop not found via email
                return "Shop not found";
            }

            Shop shop = shopOpt.get();

            // check password
            if (!shop.getPassword().equals(password)) {
                return "Wrong password";
            }

            // successful login
            return "Shop login successful";

        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Neither shop, neither citizen");
    }
}
