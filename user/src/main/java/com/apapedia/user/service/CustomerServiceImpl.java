package com.apapedia.user.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import com.apapedia.user.model.Customer;
import com.apapedia.user.payload.CartDTO;
import com.apapedia.user.payload.frontend.UserDTO;
import com.apapedia.user.payload.user.RegisterRequestDTO;
import com.apapedia.user.repository.CustomerDb;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerDb customerDb;

    private Authentication userAuthentication;

    private final WebClient webClient;

    public CustomerServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8083")
                            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .build();
    }

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

    @Override
    public Customer getCustomerById(UUID fromString) {
        for (Customer customer : getAllCustomers()) {
            if (customer.getId().equals(fromString)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public Customer create(RegisterRequestDTO registerRequest) {
        Customer user = new Customer();
        user.setNama(registerRequest.getNama());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setAddress(registerRequest.getAddress());
        user.setBalance((long) 0);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole("customer");
        user.setCartId(UUID.randomUUID());
        save(user);

        createCart(user);
        return user;
    }

    private void createCart(Customer user) {
        var userId = user.getId();
        CartDTO response = this.webClient
                .post()
                .uri("/api/cart/create/user?userId={userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CartDTO.class)
                .block();

        user.setCartId(response.getCartId());
        save(user);
    }

    @Override
    public void deleteCustomer(Customer customer) throws NoSuchElementException {
        if (customer != null) {
            customerDb.deleteById(customer.getId());
        } else {
            throw new NoSuchElementException();
        }
    }

}
