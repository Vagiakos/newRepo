package com.example.eshop.Services;

import java.util.Optional;

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


}