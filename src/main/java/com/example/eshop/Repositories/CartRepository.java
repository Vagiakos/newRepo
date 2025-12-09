package com.example.eshop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eshop.Models.Cart;

public interface CartRepository extends JpaRepository<Long, Cart> {

}
