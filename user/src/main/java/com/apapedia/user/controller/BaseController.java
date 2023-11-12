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

import com.apapedia.user.config.jwt.JwtService;
import com.apapedia.user.model.Seller;
import com.apapedia.user.payload.JwtResponse;
import com.apapedia.user.payload.LoginRequest;
import com.apapedia.user.payload.RegisterRequest;
import com.apapedia.user.repository.SellerDb;
import com.apapedia.user.repository.UserDb;
import com.apapedia.user.service.SellerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class BaseController {
    // delete to use spring login
    @Autowired
    private AuthenticationManager authenticationManager;

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
    private String home(Model model, HttpServletRequest request) {
        // belum berhasil login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails ) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            logger.info(username);
            model.addAttribute("username", username);
        } else {
            logger.info("not logged in");
        }
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // // Retrieve the JWT token from the authentication object
        // if (authentication != null) {
        //     String token = authentication.getDetails().toString();

        //     // Extract user details from the token's claims
        //     if (!token.isEmpty()) {
        //         // Claims claims = Jwts.parser().setSigningKey("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437").parseClaimsJws(token).getBody();
        //         // String username = jwtService.extractUsername(token);
    
        //         logger.info(token);
        //     } else {
        //         logger.info("hm");
        //     }
        // } else {
        //     logger.info("not logged in");
        // }

        return "home";
    }

    @GetMapping("/login")
    public String login(Model model){
        // model.addAttribute("loginRequest", new LoginRequest());
        return "login";
        // 
    }

    @GetMapping("/seller")
    private String seller(Model model) {
        return "seller";
    }

    @GetMapping("/signup")
    private String formRegister(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "registration";
    }

    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute RegisterRequest registerRequest, RedirectAttributes redirectAttrs) {
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
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            String jwt = jwtService.generateToken(authRequest.getUsername(), seller.getId(), roles);
            
            JwtResponse jwtResponse = new JwtResponse(jwt, seller.getId(), userDetails.getUsername(), seller.getEmail(), roles);
            logger.info(jwt);
            // logger.info(seller.hasAuthority("seller"));
            redirectAttrs.addFlashAttribute("jwtResponse", jwtResponse);
            return "redirect:/";
        } else {
            redirectAttrs.addFlashAttribute("error", "Username or password invalid");
            return "redirect:/login";
        }
    }
}
