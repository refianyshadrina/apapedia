package com.apapedia.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.apapedia.user.model.Seller;
import com.apapedia.user.payload.SellerRegisterRequest;

import jakarta.validation.Valid;

public interface SellerService {
    List<Seller> getAllSeller();

    Seller getSellerByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void save(Seller user);

    String encrypt(String password);
}
