package com.apapedia.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.Seller;
import com.apapedia.user.repository.SellerDb;

@Service
public class SellerServiceImpl implements SellerService{
    @Autowired
    SellerDb sellerDb;

    @Override
    public List<Seller> getAllSeller(){
        return sellerDb.findAll();
    }

    @Override
    public Seller getSellerByUsername(String username){
        for (Seller seller : getAllSeller()) {
            if (seller.getUsername().equalsIgnoreCase(username)) {
                return seller;
            }
        }
        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return sellerDb.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return sellerDb.existsByEmail(email);
    }

    @Override
    public void save(Seller user) {
        // user.setPassword(encrypt(user.getPassword()));
        sellerDb.save(user);
    }

    @Override
    public String encrypt(String password) {
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String hashedPassword = passwordEncoder.encode(password);
      return hashedPassword;
    }
}
