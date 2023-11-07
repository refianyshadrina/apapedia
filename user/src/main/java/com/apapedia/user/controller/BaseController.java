package com.apapedia.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import com.apapedia.user.model.Seller;
// import com.apapedia.user.model.UserModel;
import com.apapedia.user.payload.MessageResponse;
import com.apapedia.user.payload.SellerRegisterRequest;
import com.apapedia.user.repository.SellerDb;
import com.apapedia.user.repository.UserDb;
import com.apapedia.user.service.SellerService;

import jakarta.validation.Valid;

@Controller
public class BaseController {

    @Autowired
    UserDb userDb;

    @Autowired
    SellerDb sellerDb;

    // @Qualifier("sellerServiceImpl")

    @Autowired
    SellerService sellerService;

    // Logger logger = LoggerFactory.getLogger(BaseController.class);

    @GetMapping("/")
    private String home(Model model) {

        return "home";
    }

    @GetMapping("/signup")
    private String formRegister(Model model) {
        model.addAttribute("registerRequest", new SellerRegisterRequest());
        return "registration";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @ModelAttribute SellerRegisterRequest registerRequest) {
        if (sellerService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username already in use!"));
        }
        if (sellerService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email already in use!"));
        }

        Seller user = new Seller();
        user.setNama(registerRequest.getNama());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setAddress(registerRequest.getAddress());
        user.setBalance((long) 0);
        user.setCategory(registerRequest.getCategory());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        // Set other user properties
        sellerService.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
