package com.example.eshop.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.eshop.Models.Product;
//use JpaSpecificationExecutor for criteria queries                                                          
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {

} 