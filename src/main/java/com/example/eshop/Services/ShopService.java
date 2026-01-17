package com.example.eshop.Services;

import java.util.Optional;

import com.example.eshop.ErrorHandling.AlreadyExistsException;
import com.example.eshop.ErrorHandling.InvalidCredentialsException;
import com.example.eshop.ErrorHandling.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.eshop.Models.Shop;
import com.example.eshop.Repositories.ShopRepository;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;



    public void updateShop(Long afm, String username, String email, String brand, String owner, String password){
        Optional<Shop> optionalShop = shopRepository.findById(afm);
        if(!optionalShop.isPresent())
            throw new NotFoundException("Shop not found!");
        Shop shop = optionalShop.get();

        if(username!=null)
            shop.setUsername(username);
        if(email!=null) {
            if (!email.contains("@"))
                throw new InvalidCredentialsException("Invalid Email!");
            shop.setEmail(email);
        }
        // check if email already exists
        if (shopRepository.findByEmail(email).isPresent()){
            throw new AlreadyExistsException("Email already exists!");
        }
        if(brand!=null)
            shop.setBrand(brand);
        if(owner!=null)
            shop.setOwner(owner);

        if (password != null){
            if(password.length() < 6)
                throw new InvalidCredentialsException("Password must be 6 character or more");
            shop.setPassword(password);
        }

        shopRepository.save(shop);
    }

}