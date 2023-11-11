package com.apapedia.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;
import org.slf4j.Logger;

import com.apapedia.user.config.SellerDetailsImpl;
import com.apapedia.user.config.jwt.JwtService;
import com.apapedia.user.config.jwt.JwtUtils;
import com.apapedia.user.model.Seller;
import com.apapedia.user.payload.JwtResponse;
import com.apapedia.user.payload.LoginRequest;
import com.apapedia.user.payload.SellerRegisterRequest;
import com.apapedia.user.repository.SellerDb;
import com.apapedia.user.repository.UserDb;
import com.apapedia.user.service.SellerService;

import jakarta.validation.Valid;

@Controller
public class BaseController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtService jwtService;

    @Autowired
    UserDb userDb;

    @Autowired
    SellerDb sellerDb;

    @Qualifier("sellerServiceImpl")

    @Autowired
    SellerService sellerService;

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    @GetMapping("/")
    private String home(Model model) {
        // belum berhasil login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        if (authentication != null ) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                SellerDetailsImpl userDetails = (SellerDetailsImpl) authentication.getPrincipal();
                String username = userDetails.getUsername();
                logger.info(username);
                model.addAttribute("username", username);
            }


        } else {
            logger.info("not logged in");
        }
        logger.info(authentication.getPrincipal().toString());
        
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
        // 
    }

    @GetMapping("/seller")
    private String seller(Model model) {
        return "seller";
    }

    @GetMapping("/signup")
    private String formRegister(Model model) {
        model.addAttribute("registerRequest", new SellerRegisterRequest());
        return "registration";
    }

    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute SellerRegisterRequest registerRequest, RedirectAttributes redirectAttrs) {
        if (sellerService.existsByUsername(registerRequest.getUsername())) {
            redirectAttrs.addFlashAttribute("error", "Username already in use");
            return "redirect:/signup";
        }
        if (sellerService.existsByEmail(registerRequest.getEmail())) {
            redirectAttrs.addFlashAttribute("error", "Email already in use");
            return "redirect:/signup";
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
        user.setRole("seller");
        sellerService.save(user);

        redirectAttrs.addFlashAttribute("success", "Please login to system");
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@ModelAttribute LoginRequest authRequest, RedirectAttributes redirectAttrs) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        sellerService.setAuthentication(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            Seller seller = sellerService.getSellerByUsername(authRequest.getUsername());
            SellerDetailsImpl userDetails = (SellerDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            String jwt = jwtService.generateToken(authRequest.getUsername(), seller.getId(), roles);
            
            JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getUuid(), userDetails.getUsername(), userDetails.getEmail(), roles);
            logger.info(jwt);
            logger.info(userDetails.hasAuthority("seller"));
            redirectAttrs.addFlashAttribute("jwtResponse", jwtResponse);
            return "redirect:/";
        } else {
            redirectAttrs.addFlashAttribute("error", "Username or password invalid");
            return "redirect:/login";
        }
    }
}
