package com.example.eshop.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eshop.Models.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {

}