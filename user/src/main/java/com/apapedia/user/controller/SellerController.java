package com.apapedia.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;
import java.util.List;
import java.util.UUID;
import java.util.Arrays;
import org.slf4j.Logger;
import jakarta.servlet.http.Cookie;
import com.apapedia.user.config.jwt.JwtService;
import com.apapedia.user.model.Seller;
import com.apapedia.user.payload.JwtResponse;
import com.apapedia.user.payload.LoginRequest;
import com.apapedia.user.payload.RegisterRequest;
import com.apapedia.user.repository.SellerDb;
import com.apapedia.user.repository.UserDb;
import com.apapedia.user.service.SellerService;
import com.apapedia.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class SellerController {
    private static final long EXPIRATION_TIME = 3600000;

    Logger logger = LoggerFactory.getLogger(SellerController.class);

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

    @Qualifier("userServiceImpl")

    @Autowired
    UserService userService;

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        return "redirect:/";
    }

    @GetMapping("/seller/{id}")
    public String viewSeller(@PathVariable("id") UUID id, Model model) {
        var seller = sellerService.getSellerById(id);
        model.addAttribute("seller", seller);

        return "view-seller";
    }

    @GetMapping("/seller")
    private String seller(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, RedirectAttributes redirectAttrs) {

        if (jwtToken != null) {
            if (jwtToken.equals("no text") || jwtToken.equals("") || jwtToken.equals(" ")) {
                redirectAttrs.addFlashAttribute("error", "Username or password invalid");
                return "redirect:/login";
            } else {
                Claims claims = jwtService.extractAllClaims(jwtToken);
                logger.info(claims.getSubject());
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "You need to login as a seller to access this page");
            return "redirect:/login";
        }
        return "seller";
    }

    @GetMapping("/signup")
    private String formRegister(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "registration";
    }

    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute RegisterRequest registerRequest, RedirectAttributes redirectAttrs) {
        if (userService.existsByUsername(registerRequest.getUsername())) {
            redirectAttrs.addFlashAttribute("error", "Username already in use");
            return "redirect:/signup";
        }
        if (userService.existsByEmail(registerRequest.getUsername())) {
            redirectAttrs.addFlashAttribute("error", "Email already in use");
            return "redirect:/signup";
        }

        sellerService.create(registerRequest);

        redirectAttrs.addFlashAttribute("success", "Please login to system");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@ModelAttribute LoginRequest authRequest, RedirectAttributes redirectAttrs, HttpServletResponse response) {
        System.out.println("masuk login");
        // Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        try {
            
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            System.out.println("masuk1");
            
            System.out.println("masuk2");
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (authentication.isAuthenticated()) {
                System.out.println("masuk terautentikasi");
                Seller seller = sellerService.getSellerByUsername(authRequest.getUsername());
                if (seller == null) {
                    redirectAttrs.addFlashAttribute("error", "Are you a customer?");
                    return "redirect:/login";
                }
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                List<String> roles = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());
                String jwt = jwtService.generateToken(authRequest.getUsername(), seller.getId(), roles);
                
                
                JwtResponse jwtResponse = new JwtResponse(jwt, seller.getId(), userDetails.getUsername(), seller.getEmail(), roles);
                logger.info(jwt);

                List<String> paths = Arrays.asList("/", "/seller/");

                for (String path : paths) {
                    Cookie cookie = new Cookie("jwtToken", jwt);
                    cookie.setMaxAge((int) (EXPIRATION_TIME / 1000));
                    cookie.setHttpOnly(true);
                    cookie.setPath(path);
                    response.addCookie(cookie);
                }
                redirectAttrs.addFlashAttribute("jwtResponse", jwtResponse);
                return "redirect:/";
            } else {
                redirectAttrs.addFlashAttribute("error", "Username or password invalid");
                return "redirect:/login";
            }
        } catch (UsernameNotFoundException | BadCredentialsException | IOException e) {
            redirectAttrs.addFlashAttribute("error", "Username or password invalid");
            return "redirect:/login";

        } 
    }

    @GetMapping("/seller/{id}/delete")
    public String deleteSeller(@PathVariable("id") UUID id, Model model) {
        var seller = sellerService.getSellerById(id);
        sellerService.deleteSeller(seller);
        model.addAttribute("id", id);
        return "";
    }


}
