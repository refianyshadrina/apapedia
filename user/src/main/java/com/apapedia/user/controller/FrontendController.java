package com.apapedia.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.LoggerFactory;
import java.util.UUID;
import org.slf4j.Logger;
import com.apapedia.user.config.jwt.JwtService;
import com.apapedia.user.model.Seller;
import com.apapedia.user.model.UserModel;
import com.apapedia.user.payload.JwtResponse;
import com.apapedia.user.payload.LoginRequest;
import com.apapedia.user.payload.RegisterRequest;
import com.apapedia.user.payload.UpdateBalanceUser;
import com.apapedia.user.payload.UpdateUserRequestDTO;
import com.apapedia.user.restservice.UserRestService;
import com.apapedia.user.service.FrontEndService;
import com.apapedia.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
// seller controller
public class FrontendController {

    Logger logger = LoggerFactory.getLogger(FrontendController.class);

    @Autowired
    private JwtService jwtService;

    @Qualifier("userServiceImpl")

    @Autowired
    UserService userService;

    @Qualifier("userRestServiceImpl")
    @Autowired
    UserRestService userRestService;

    @Autowired
    FrontEndService frontEndService;

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        return "redirect:/";
    }

    @GetMapping("/signup")
    private String formRegister(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "registration";
    }

    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute RegisterRequest registerRequest, RedirectAttributes redirectAttrs) {

        try {
            userRestService.signUp(registerRequest);
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/signup";
        }

        redirectAttrs.addFlashAttribute("success", "Please login to system");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    // sebagai front end
    @PostMapping("/login")
    public String authenticateAndGetToken(@ModelAttribute LoginRequest authRequest, RedirectAttributes redirectAttrs, HttpServletResponse response) {
        
        try {
            JwtResponse jwt = userRestService.login(authRequest);
            String jwtString = jwt.getToken();

            // set cookie
            frontEndService.setCookie(response, jwtString);

            System.out.println(jwtString);
            return "redirect:/";
        // } catch (UsernameNotFoundException | BadCredentialsException | IOException e) {
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";

        } 

    }

    @GetMapping("/deleteseller")
    public String deleteSeller(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login";
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);

            Seller seller = (Seller) userRestService.getUser(id, jwtToken);

            userRestService.deleteUser(seller.getId());

            return "redirect:/logout";
        }
        
    }

    @GetMapping("/sellerprofile")
    private String profilePage(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login";
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);

            try {
                Seller seller = (Seller) userRestService.getUser(id, jwtToken);
                model.addAttribute("seller", seller);

                return "profile";
            } catch (RuntimeException e) {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/updateprofile")
    private String updateProfileForm(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login";
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);
            var user = userRestService.getUser(id, jwtToken);

            var updateRequest = new UpdateUserRequestDTO();
            updateRequest.setId(id);
            updateRequest.setAddress(user.getAddress());
            updateRequest.setEmail(user.getEmail());
            updateRequest.setNama(user.getNama());
            // updateRequest.setPassword(user.getPassword());
            updateRequest.setUsername(user.getUsername());

            model.addAttribute("updateRequest", updateRequest);
            return "update-profile-form";
        }
    }

    @PostMapping("/updateprofile")
    private String update(Model model, @ModelAttribute UpdateUserRequestDTO updateRequest, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login";
        } else {

            try {
                JwtResponse jwt = userRestService.update(updateRequest);

                frontEndService.setCookie(response, jwt.getToken());

                Seller seller = (Seller) userService.getUserByUsername(jwt.getUsername());

                redirectAttrs.addFlashAttribute("seller", seller);
                return "redirect:/sellerprofile";
            
            } catch (RuntimeException e) {
                
                redirectAttrs.addFlashAttribute("error", e.getMessage());
                return "redirect:/updateprofile";

            } 
        }
    }

    @GetMapping("/withdraw")
    private String withdrawBalance(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login";
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);
            var user = userRestService.getUser(id, jwtToken);

            var updateRequest = new UpdateBalanceUser();
            updateRequest.setId(id);
            updateRequest.setBalance(0);

            model.addAttribute("updateRequest", updateRequest);
            return "update-balance-form";
        }
    }

    @PostMapping("/withdraw")
    private String withdrawBalanceSuccess(Model model, @ModelAttribute UpdateBalanceUser updateRequest, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
            return "redirect:/login";
        } else {

            try {

                UserModel user = userRestService.updateBalance(updateRequest);

                redirectAttrs.addFlashAttribute("seller", user);
                return "redirect:/sellerprofile";
            
            } catch (RuntimeException e) {
                
                redirectAttrs.addFlashAttribute("error", e.getMessage());
                return "redirect:/withdraw";

            } 
        }
    }


}
