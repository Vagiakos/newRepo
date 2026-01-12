package com.example.eshop.Models;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    private Long afm;

    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String typeOfUser;


    public User(Long afm, String username, String email, String password) {
        this.afm = afm;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public Long getAfm() {
        return afm;
    }

    public void setAfm(Long afm) {
        this.afm = afm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void settypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }
}
