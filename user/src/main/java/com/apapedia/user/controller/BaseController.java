package com.apapedia.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Qualifier("sellerServiceImpl")

    @Autowired
    SellerService sellerService;

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    @GetMapping("/")
    private String home(Model model) {
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // User user = (User) auth.getPrincipal();
        // String username = user.getUsername();
        // if(sellerService.getSellerByUsername(username)!=null) {
        //     Seller userLoggedIn = sellerService.getSellerByUsername(username);
        //     model.addAttribute("user", userLoggedIn);
        //     logger.info("Seller {} logged in", userLoggedIn.getUsername());
        // } 
        // return "home";

        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // String roleCurrentUser = authentication.getAuthorities().toString();
        // logger.info("logged in: ", roleCurrentUser);
        // model.addAttribute("roleCurrentUser", roleCurrentUser);
        // return "home";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            logger.info(username);
            model.addAttribute("username", username);
        } else {
            logger.info("not logged in");
        }
        
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping("/seller")
    private String seller(Model model) {
        return "seller";
    }

    // @GetMapping("/login")
    // public String login(Model model, String error, String logout) {
    //     if (error != null) {
    //         model.addAttribute("error", "Invalid username or password");
    //     }

    //     if (logout != null) {
    //         model.addAttribute("message", "Logged out successfully");
    //     }

    //     return "login";
    // }

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
}
