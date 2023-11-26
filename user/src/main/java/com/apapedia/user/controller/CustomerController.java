package com.apapedia.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.slf4j.Logger;

import com.apapedia.user.config.jwt.JwtService;
import com.apapedia.user.model.Customer;
import com.apapedia.user.payload.JwtResponse;
import com.apapedia.user.payload.LoginRequest;
import com.apapedia.user.repository.SellerDb;
import com.apapedia.user.repository.UserDb;
import com.apapedia.user.service.CustomerService;
import com.apapedia.user.service.UserService;

import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;

@RestController
public class CustomerController {
    // delete to use spring login
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtService jwtService;

    @Autowired
    UserDb userDb;

    @Autowired
    SellerDb sellerDb;

    @Qualifier("customerServiceImpl")

    @Autowired
    CustomerService customerService;

    @Qualifier("userServiceImpl")

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(CustomerController.class);


    // @PostMapping("/api/register")
    // @ResponseBody
    // public ResponseEntity<?> createCustomer(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult) {
    //     if (bindingResult.hasFieldErrors()) {
    //         throw new ResponseStatusException(
    //                 HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
    //         );
    //     } else {
    //         if (userService.existsByUsername(registerRequest.getUsername())) {
    //             return ResponseEntity.ok("Username  already in use");
    //         }
    //         if (userService.existsByEmail(registerRequest.getEmail())) {
    //             return ResponseEntity.ok("Email  already in use");
    //         }

    //         Customer customer  = customerService.create(registerRequest);

    //         return new ResponseEntity<>(customer, HttpStatus.CREATED);
    //     }
    // }

    @GetMapping(value = "/api/customer/{id}")
    private Customer retrieveBuku(@PathVariable("id") String id) {
        try {
            return customerService.getCustomerById(UUID.fromString(id)); 
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id " + id + " not found");
        }
    }

    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (authentication.isAuthenticated()) {
                Customer customer = customerService.getCustomerByUsername(loginRequest.getUsername());
                if (customer == null) {
                    return ResponseEntity.ok("Are you a seller?");
                }
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                List<String> roles = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());
                String jwt = jwtService.generateToken(loginRequest.getUsername(), customer.getId(), roles);
        
                JwtResponse jwtResponse = new JwtResponse(jwt, customer.getId(), userDetails.getUsername(), customer.getEmail(), roles);
                
                return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
            } else {
                return ResponseEntity.ok("could not find username");
            }
        } catch (UsernameNotFoundException | BadCredentialsException | IOException e) {
            return ResponseEntity.ok("Username or password invalid");
        }
        
    }

    @DeleteMapping(value = "/api/customer/{id}/delete")
    private ResponseEntity<?> deleteCustomer(@PathVariable("id") UUID id) {
        var customer = customerService.getCustomerById(id);
        try {
            customerService.deleteCustomer(customer);
            return ResponseEntity.ok("Customer dengan id " + String.valueOf(id) + " Deleted!");

        } catch (NoSuchElementException | IOException | BadCredentialsException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Customer dengan id " + String.valueOf(id) + " Not Found!"
            );

        } 
    }

}


