package com.apapedia.user.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.apapedia.user.model.Customer;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer getCustomerByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void save(Customer user);

    String encrypt(String password);

    void setAuthentication(Authentication newAuthentication);

    Authentication getAuthentication();
}
