package com.example.eshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.eshop.Models.Cart;
import com.example.eshop.Models.Citizen;
import com.example.eshop.Models.Product;
import com.example.eshop.Models.Shop;
import com.example.eshop.Repositories.CartRepository;
import com.example.eshop.Repositories.CitizenRepository;
import com.example.eshop.Repositories.ProductRepository;
import com.example.eshop.Repositories.ShopRepository;
import com.example.eshop.Repositories.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    CitizenRepository citizenRepository;

    @Autowired
    CartRepository cartRepository;

    @Override
    public void run(String... args) {

        initCitizens();
        initShops();
    }

    private void initCitizens() {
        Citizen citizen = new Citizen();
        citizen.setAfm(123456789L);
        citizen.setUsername("Vagiakos");
        citizen.settypeOfUser("Citizen");
        citizen.setName("Vagios");
        citizen.setSurname("Rizoulis");
        citizen.setEmail("vagios@gmail.com");
        citizen.setPassword("123456");

        Cart cart = new Cart();
        citizen.setCart(cart);
        citizenRepository.save(citizen);

        Citizen citizen2 = new Citizen();
        citizen2.setAfm(164823654L);
        citizen2.setUsername("Dimitris");
        citizen2.settypeOfUser("Citizen");
        citizen2.setName("Dimitris");
        citizen2.setSurname("Dimoudis");
        citizen2.setEmail("dimitris@gmail.com");
        citizen2.setPassword("654321");

        Cart cart2 = new Cart();
        citizen2.setCart(cart2);
        citizenRepository.save(citizen2);
    }

    private void initShops() {
        Shop shop = new Shop();
        shop.setAfm(254386109L);
        shop.setUsername("FirstShop");
        shop.settypeOfUser("Shop");
        shop.setOwner("George");
        shop.setBrand("Nike");
        shop.setEmail("nike@gmail.com");
        shop.setPassword("123456789");

        shopRepository.save(shop);
        userRepository.save(shop);

        Product p1 = new Product(
                "Nike Air Force 1",
                "Shoes",
                "Sneakers",
                100.00,
                10
        );
        p1.setShop(shop);
        productRepository.save(p1);

        Product p2 = new Product(
                "Nike 2",
                "Shoes",
                "Sneakers",
                130.99,
                15
        );
        p2.setShop(shop);
        productRepository.save(p2);

        Shop shop2 = new Shop();
        shop2.setAfm(987654321L);
        shop2.setUsername("SecondShop");
        shop2.settypeOfUser("Shop");
        shop2.setOwner("Dimitris");
        shop2.setBrand("Adidas");
        shop2.setEmail("adidas@gmail.com");
        shop2.setPassword("987654321");

        shopRepository.save(shop2);
        userRepository.save(shop2);

        Product p3 = new Product(
                "Adidas 2",
                "Shoes",
                "Sneakers",
                120.00,
                30
        );
        p3.setShop(shop2);
        productRepository.save(p3);

        Product p4 = new Product(
                "Adidas 1",
                "Shoes",
                "Sneakers",
                89.99,
                20
        );
        p4.setShop(shop2);
        productRepository.save(p4);
    }
}
