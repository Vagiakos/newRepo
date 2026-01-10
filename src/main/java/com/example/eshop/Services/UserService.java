package com.example.eshop.Services;

import com.example.eshop.DTOs.RegisterRequest;
import com.example.eshop.Models.Cart;
import com.example.eshop.Models.Citizen;
import com.example.eshop.Models.Shop;
import com.example.eshop.Models.User;
import com.example.eshop.Repositories.CitizenRepository;
import com.example.eshop.Repositories.ShopRepository;
import com.example.eshop.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    CitizenRepository citizenRepository;

    //login method
    public String login(String typeOfUser, String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");

        User user = optionalUser.get();
        if(typeOfUser.equals("Shop")){
            //check password
            if (!user.getPassword().equals(password)) {
                //wrong password
                return "Wrong password";
            }
            //successful login
            return "Shop login successful";
        }else{
            //check password
            if (!user.getPassword().equals(password)) {
                //wrong password
                return "Wrong password";
            }
            //successful login
            return "Citizen login successful";
        }


    }


    public void register(RegisterRequest registerRequest) {

        if(registerRequest.getTypeOfUser().equals("Citizen")){
            // Validation email
            if (registerRequest.getEmail() == null || !registerRequest.getEmail().contains("@")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Email");
            }

            // Validation password
            if (registerRequest.getPassword() == null || registerRequest.getPassword().length() < 6) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 characters");
            }

            // Validation name and surname
            if (registerRequest.getName() == null || registerRequest.getName().isBlank() ||
                    registerRequest.getSurname() == null || registerRequest.getSurname().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name and surname cannot be empty");
            }

            // check if email already exists
            if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists!");
            }

            Citizen citizen = new Citizen();
            citizen.setUsername(registerRequest.getUsername());
            citizen.settypeOfUser(registerRequest.getTypeOfUser());
            citizen.setAfm(registerRequest.getAfmCitizen());
            citizen.setName(registerRequest.getName());
            citizen.setSurname(registerRequest.getSurname());
            citizen.setEmail(registerRequest.getEmail());
            citizen.setPassword(registerRequest.getPassword());
            Cart cart = new Cart();
            citizen.setCart(cart);
            citizenRepository.save(citizen);
            userRepository.save(citizen);

        } else if (registerRequest.getTypeOfUser().equals("Shop")) {
            // Validation email
            if (registerRequest.getEmail() == null || !registerRequest.getEmail().contains("@")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Email");
            }

            // Validation password
            if (registerRequest.getPassword() == null || registerRequest.getPassword().length() < 6) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 characters");
            }

            // check if email already exists
            if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists!");
            }

            Shop shop = new Shop();
            shop.setUsername(registerRequest.getUsername());
            shop.settypeOfUser(registerRequest.getTypeOfUser());
            shop.setAfm(registerRequest.getAfmShop());
            shop.setOwner(registerRequest.getOwner());
            shop.setBrand(registerRequest.getBrandShop());
            shop.setEmail(registerRequest.getEmail());
            shop.setPassword(registerRequest.getPassword());
            shopRepository.save(shop);
            userRepository.save(shop);
        }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong selected user");

    }
}
