package com.example.eshop.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eshop.Models.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long>{
    //use Optional to handle possible null values
    Optional<Shop> findByEmail(String email); //search via shop email 
}
