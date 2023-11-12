package com.apapedia.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.Customer;
import com.apapedia.user.repository.CustomerDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerDb customerDb;

    private Authentication userAuthentication;

    @Override
    public List<Customer> getAllCustomers(){
        return customerDb.findAll();
    }

    @Override
    public Customer getCustomerByUsername(String username){
        for (Customer customer : getAllCustomers()) {
            if (customer.getUsername().equalsIgnoreCase(username)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return customerDb.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerDb.existsByEmail(email);
    }

    @Override
    public void save(Customer user) {
        user.setPassword(encrypt(user.getPassword()));
        customerDb.save(user);
    }

    @Override
    public String encrypt(String password) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String hashedPassword = passwordEncoder.encode(password);
      return hashedPassword;
    }


    @Override
    public Authentication getAuthentication(){
        return this.userAuthentication;
    }

    public void setAuthentication(Authentication newAuthentication){
        this.userAuthentication = newAuthentication;
    }


}
