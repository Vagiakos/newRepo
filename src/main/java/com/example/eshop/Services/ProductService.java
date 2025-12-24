package com.example.eshop.Services;

import com.example.eshop.Models.Product;
import com.example.eshop.Models.Shop;
import com.example.eshop.Repositories.ProductRepository;
import com.example.eshop.Repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShopRepository shopRepository;

    public List<Product> getProductsByFilters(String brand, String type, Double price) {

        //το Specificaton λειτουργει σαν sql query με το root να αναφερεται στον πινακα το και το criteriabuilder ως where, και ουσιαστικα με το and ενωνει τις συνθηκες
        //αμα ο χρηστης δεν βαλει κανενα φιλτρο δηλαδη ερθουν ολα null τοτε θα επιστραφουν ολα τα products select * from product δεν υπαρχουν συνθηκες
        Specification<Product> spec = Specification.where(((root, query, criteriaBuilder) -> criteriaBuilder.conjunction()));
        if(brand !=null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.lower(root.get("brand")), brand.toLowerCase()));
        }

        if(type !=null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.lower(root.get("type")), type.toLowerCase()));
        }

        if(price !=null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price));
        }


        return productRepository.findAll(spec);

    }

    public List<Product> getAllProducts() {
        return (List<Product>)productRepository.findAll();
    }

    public List<Product> getProductsFromShop(Long afm){
        Optional<Shop> optionalShop = shopRepository.findById(afm);
        if(!optionalShop.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found");
        Shop shop = optionalShop.get();
        return shop.getProducts();

    }


    public Product getProduct(String brand){
        Optional<Product> optionalProduct = productRepository.findById(brand);
        if(!optionalProduct.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        Product product = optionalProduct.get();
        return product;
    }
}