package com.example.eshop.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eshop.Models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}