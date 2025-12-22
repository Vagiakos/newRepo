package com.example.eshop.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eshop.Models.Citizen;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    //use Optional to handle possible null values
    Optional<Citizen> findByEmail(String email); //search via citizen email
}