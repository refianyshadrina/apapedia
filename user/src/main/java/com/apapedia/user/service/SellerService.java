package com.apapedia.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.apapedia.user.model.Customer;
import com.apapedia.user.model.Seller;
import com.apapedia.user.payload.RegisterRequest;

import org.springframework.security.core.Authentication;


public interface SellerService{
    List<Seller> getAllSeller();

    Seller getSellerByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void save(Seller user);

    String encrypt(String password);

    void setAuthentication(Authentication newAuthentication);

    Authentication getAuthentication();

    Seller getSellerById(UUID id);

    void create(RegisterRequest user);

    void deleteSeller(Seller seller);
}
