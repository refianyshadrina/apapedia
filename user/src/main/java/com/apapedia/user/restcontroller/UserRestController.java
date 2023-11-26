package com.apapedia.user.restcontroller;

import com.apapedia.user.model.Customer;
import com.apapedia.user.model.Seller;
import com.apapedia.user.model.UserModel;
import com.apapedia.user.payload.JwtResponse;
import com.apapedia.user.payload.LoginRequest;
import com.apapedia.user.payload.MessageResponse;
import com.apapedia.user.payload.RegisterRequest;
import com.apapedia.user.payload.UpdateBalanceUser;
import com.apapedia.user.payload.UpdateUserRequestDTO;
import com.apapedia.exception.InsufficientBalanceException;
import com.apapedia.exception.UserNotFoundException;
import com.apapedia.user.config.jwt.JwtService;
import com.apapedia.user.service.CustomerService;
import com.apapedia.user.service.SellerService;
import com.apapedia.user.service.UserService;

import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
public class UserRestController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService usersService;
    @Autowired
    JwtService jwtUtils;

    @Qualifier("customerServiceImpl")
    @Autowired
    CustomerService customerService;


    @Qualifier("sellerServiceImpl")
    @Autowired
    SellerService sellerService;    

    Logger logger = LoggerFactory.getLogger(UserRestController.class);

    // akan disesuaikan dengan kebutuhan
    @GetMapping(value = "/detail/{id}")
    @ResponseBody
    public ResponseEntity<?> retrieveUser(@PathVariable("id") UUID id){
        UserModel user = usersService.getUserById(id);
        System.out.println(user == null);
        if (user != null) {
            if (user instanceof Seller) {
                user = (Seller) user;
            } else {
                user = (Customer) user;
            }
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().body("Cant find customer with that id");
        }
    }


    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") UUID id){
        UserModel user = usersService.getUserById(id);
        if (user == null) {
            return ResponseEntity.badRequest().body("Cant find user with that id");
        }
        usersService.deleteUser(user);
        return ResponseEntity
                .ok()
                .body(new MessageResponse("Success delete user"));
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            if (usersService.existsByUsername(registerRequest.getUsername())) {
                return ResponseEntity.badRequest().body("Username alread in use!");
            }
            if (usersService.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest().body("Email  already in use");
            }
            // customer
            if (registerRequest.getCategory() == null) {
                Customer customer = customerService.create(registerRequest);
                return new ResponseEntity<>(customer, HttpStatus.CREATED);
            } else { // seller
                Seller seller = sellerService.create(registerRequest);
                return new ResponseEntity<>(seller, HttpStatus.CREATED);
            }
        }
    }
    // @PostMapping(value = "/v2/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    // login seller, di authenticatenya di frontend.
    @PostMapping("/v2/login")
    @ResponseBody
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (authentication.isAuthenticated()) {
                Seller seller = sellerService.getSellerByUsername(authRequest.getUsername());
                if (seller == null) {
                    return ResponseEntity.badRequest().body("If you're a customer, you can login with our mobile app");
                }
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                List<String> roles = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());
                String jwt = jwtUtils.generateToken(authRequest.getUsername(), seller.getId(), roles);
        
                JwtResponse jwtResponse = new JwtResponse(jwt, seller.getId(), userDetails.getUsername(), seller.getEmail(), roles);
                
                return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
            } else {
                return ResponseEntity.badRequest().body("could not find username");
            }
        } catch (UsernameNotFoundException | BadCredentialsException | IOException e) {
            return ResponseEntity.badRequest().body("Username or password invalid");
        }
            
    }

    @PostMapping("/generate-new-token")
    @ResponseBody
    public ResponseEntity<?> generateNewJwt(@Valid @RequestBody LoginRequest authRequest) {
        UserModel user = usersService.getUserByUsername(authRequest.getUsername());
        if (user == null) {
            return ResponseEntity.badRequest().body("If you're a customer, you can login with our mobile app");
        }
        List<String> roles = new ArrayList<>();
        roles.add(user.getRole());
        String jwt = jwtUtils.generateToken(authRequest.getUsername(), user.getId(), roles);
    
        JwtResponse jwtResponse = new JwtResponse(jwt, user.getId(), user.getUsername(), user.getEmail(), roles);
        
        return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
    }

    // login customer
    @PostMapping("/v1/login")
    @ResponseBody
    public ResponseEntity<?> authenticateCustomer(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (authentication.isAuthenticated()) {
                Customer customer = customerService.getCustomerByUsername(loginRequest.getUsername());
                if (customer == null) {
                    return ResponseEntity.badRequest().body("Are you a seller?");
                }
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                List<String> roles = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());
                String jwt = jwtUtils.generateToken(loginRequest.getUsername(), customer.getId(), roles);
        
                JwtResponse jwtResponse = new JwtResponse(jwt, customer.getId(), userDetails.getUsername(), customer.getEmail(), roles);
                
                return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
            } else {
                return ResponseEntity.badRequest().body("could not find username");
            }
        } catch (UsernameNotFoundException | BadCredentialsException | IOException e) {
            return ResponseEntity.badRequest().body("Username or password invalid");
        }
        
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequestDTO updateRequest) {
        if (usersService.isEmailExistsById(updateRequest.getEmail(), updateRequest.getId())) {
            return ResponseEntity.badRequest().body("Email already in use!");
        }

        if (usersService.isUsernameExistsById(updateRequest.getUsername(), updateRequest.getId())) {
            return ResponseEntity.badRequest().body("Username already in use!");
        }

        UserModel user = usersService.update(updateRequest);

        if (user instanceof Seller) {
            Seller seller = (Seller) user;
            return new ResponseEntity<>(seller, HttpStatus.CREATED);

        } else {
            Customer customer = (Customer) user;
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        }
        
    }

    // @PutMapping("/update-balance/{id}")
    // public ResponseEntity<?> updateBalance(){
    //     UserModel user = usersService.getUserById(updateBalanceUser.getId());

    //     if (user != null) {
    //         // Calls api for total price from order service
    //         Long newBalance = (long) 10;
    //         usersService.updateBalance(user, newBalance);
    //         return ResponseEntity.ok(user);
    //     } else {
    //         return ResponseEntity.badRequest().body("Cant find customer with that id");
    //     }
    // }

    @PutMapping("/self-update-balance")
    public ResponseEntity<?> selfUpdateBalance(@Valid @RequestBody UpdateBalanceUser updateBalanceUser){

        try {
            usersService.updateBalance(updateBalanceUser.getId(), updateBalanceUser.getBalance());
            System.out.println("masuk sini?");
            return ResponseEntity.ok(usersService.getUserById(updateBalanceUser.getId()));
        } catch (UserNotFoundException | InsufficientBalanceException e) {
            System.out.println("masuk insufficient balance?");
            return ResponseEntity.badRequest().body(e.getMessage());
        } 

    }

}

