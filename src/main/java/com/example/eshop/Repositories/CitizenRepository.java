package com.example.eshop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eshop.Models.Citizen;

public interface CitizenRepository extends JpaRepository<Long, Citizen> {

}