package com.apapedia.user.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.apapedia.user.model.Seller;
import org.springframework.security.core.Authentication;


public interface SellerService extends UserDetailsService{
    List<Seller> getAllSeller();

    Seller getSellerByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void save(Seller user);

    String encrypt(String password);

    void setAuthentication(Authentication newAuthentication);

    Authentication getAuthentication();
}
