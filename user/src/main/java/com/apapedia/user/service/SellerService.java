package com.apapedia.user.service;

import java.util.List;
import java.util.UUID;

import com.apapedia.user.model.Seller;
import com.apapedia.user.payload.user.RegisterRequestDTO;

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

    Seller create(RegisterRequestDTO user);

    void deleteSeller(Seller seller);
}
