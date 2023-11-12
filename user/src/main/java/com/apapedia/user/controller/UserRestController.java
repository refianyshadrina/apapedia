package com.apapedia.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;

import com.apapedia.user.config.CustomerDetailsImpl;
import com.apapedia.user.config.SellerDetailsImpl;
import com.apapedia.user.config.jwt.JwtService;
import com.apapedia.user.config.jwt.JwtUtils;
import com.apapedia.user.model.Customer;
import com.apapedia.user.model.Seller;
import com.apapedia.user.payload.JwtResponse;
import com.apapedia.user.payload.LoginRequest;
import com.apapedia.user.payload.RegisterRequest;
import com.apapedia.user.repository.SellerDb;
import com.apapedia.user.repository.UserDb;
import com.apapedia.user.service.CustomerService;
import com.apapedia.user.service.SellerService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class UserRestController {
    // delete to use spring login
    // @Autowired
    // private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtService jwtService;

    @Autowired
    UserDb userDb;

    @Autowired
    SellerDb sellerDb;

    @Qualifier("customerServiceImpl")

    @Autowired
    CustomerService customerService;

    Logger logger = LoggerFactory.getLogger(UserRestController.class);


    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<?> createCustomer(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            if (customerService.existsByUsername(registerRequest.getUsername())) {
                
            }
            if (customerService.existsByEmail(registerRequest.getEmail())) {
                
            }

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
            customerService.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    // @PostMapping("/api/login")
    // public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    //     Authentication authentication = authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    //     customerService.setAuthentication(authentication);

    //     SecurityContextHolder.getContext().setAuthentication(authentication);

    //     if (authentication.isAuthenticated()) {
    //         Customer customer = customerService.getCustomerByUsername(loginRequest.getUsername());
    //         CustomerDetailsImpl userDetails = (CustomerDetailsImpl) authentication.getPrincipal();
    //         List<String> roles = userDetails.getAuthorities().stream()
    //                 .map(item -> item.getAuthority())
    //                 .collect(Collectors.toList());
    //         String jwt = jwtService.generateToken(loginRequest.getUsername(), customer.getId(), roles);
    
    //         return ResponseEntity.ok(new JwtResponse(jwt,
    //                 userDetails.getUuid(),
    //                 userDetails.getUsername(),
    //                 userDetails.getEmail(),
    //                 roles));
    //     } else {
    //         return ResponseEntity.ok("could not find username");
    //     }



    // }

}

