package com.apapedia.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;

import com.apapedia.user.model.Customer;
import com.apapedia.user.payload.user.RegisterRequestDTO;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer getCustomerByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void save(Customer user);

    String encrypt(String password);

    void setAuthentication(Authentication newAuthentication);

    Authentication getAuthentication();

    Customer getCustomerById(UUID fromString);

    Customer create(RegisterRequestDTO registerRequest);

    void deleteCustomer(Customer customer);
}
