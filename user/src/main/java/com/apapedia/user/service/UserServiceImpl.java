package com.apapedia.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.user.repository.CustomerDb;
import com.apapedia.user.repository.SellerDb;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    SellerDb sellerDb;

    @Autowired
    CustomerDb customerDb;

    @Override
    public boolean existsByUsername(String username) {
        return sellerDb.existsByUsername(username) | customerDb.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return sellerDb.existsByEmail(email) | customerDb.existsByEmail(email);
    }
}
