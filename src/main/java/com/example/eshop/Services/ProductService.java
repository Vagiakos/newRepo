package com.example.eshop.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.eshop.DTOs.CartItemDTO;
import com.example.eshop.ErrorHandling.NotFoundException;
import com.example.eshop.Models.Cart;
import com.example.eshop.Models.Product;
import com.example.eshop.Models.Shop;
import com.example.eshop.Repositories.CartRepository;
import com.example.eshop.Repositories.ProductRepository;
import com.example.eshop.Repositories.ShopRepository;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    CartRepository cartRepository;

    public List<Product> getProductsByFilters(String brand, String type, Double priceMin, Double priceMax, Long shop_afm) {

        //το Specificaton λειτουργει σαν sql query με το root να αναφερεται στον πινακα το και το criteriabuilder ως where, και ουσιαστικα με το and ενωνει τις συνθηκες
        //αμα ο χρηστης δεν βαλει κανενα φιλτρο δηλαδη ερθουν ολα null τοτε θα επιστραφουν ολα τα products select * from product where 1 = 1, δεν υπαρχουν συνθηκες
        //criteriaBuilder.conjunction() σημαίνει “1=1”. Μετραει και την περιπτωση οπου δεν βαζει κανενα φιλτρο ο χρηστης
        Specification<Product> spec = Specification.where(((root, query, criteriaBuilder) -> criteriaBuilder.conjunction()));
        if(brand !=null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.lower(root.get("brand")), brand.toLowerCase()));
        }

        if(type !=null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.lower(root.get("type")), type.toLowerCase()));
        }

        if(priceMin !=null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceMin));
        }

        if(priceMax !=null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceMax));
        }

        if(shop_afm != null){
            //το spec δουλευει με τα entities και οχι με τον πινακα στη βαση γι αυτο παει στο αντιστοιχο shop του product και απο αυτο στο afm
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal((root.get("shop").get("afm")), shop_afm));
        }

        return productRepository.findAll(spec);

    }

    public List<Product> getAllProducts() {
        return (List<Product>)productRepository.findAll();
    }

    public List<Product> getProductsFromShop(Long afm){
        List<Product> products = new ArrayList<>();
        Optional<Shop> optionalShop = shopRepository.findById(afm);
        if(!optionalShop.isPresent())
            throw new NotFoundException("Shop not found!");
        Shop shop = optionalShop.get();
        List<Product> shopProducts = shop.getProducts();
        for(Product p : shopProducts){
            String brand = p.getBrand();
            Optional<Product> optionalProduct = productRepository.findById(brand);
            if(optionalProduct.isPresent())
                products.add(optionalProduct.get());
            else
                throw new NotFoundException("Product not found!");
        }
        return products;
    }

    public Product getProduct(String brand){
        Optional<Product> optionalProduct = productRepository.findById(brand);
        if(!optionalProduct.isPresent())
            throw new NotFoundException("Product not found!");
        Product product = optionalProduct.get();
        return product;
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public String updateProductQuantity(String brand, int quantity) {
        // find product
        Product product = productRepository.findById(brand) 
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        product.setQuantity(quantity);   // update quantity
        productRepository.save(product); // save
        
        return "Product '" + brand + "' quantity updated to " + quantity;
    }

    //delete product from shop 
    public void deleteShopProduct(String brand) {
        Product product = productRepository.findById(brand) // find product
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        // delete from db
        productRepository.delete(product);
    }
    
    // check if cart is empty 
    public List<CartItemDTO> getCartProducts(Long cartId){
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (!optionalCart.isPresent())
            throw new NotFoundException("Cart not found!");
        Cart cart = optionalCart.get();
        return cart.getCartItems().stream().map(CartItemDTO::new).toList();
    }
}