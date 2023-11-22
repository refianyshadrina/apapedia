package com.apapedia.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.apapedia.user.config.jwt.JwtService;

import io.jsonwebtoken.Claims;

@Controller
public class BaseController {

    Logger logger = LoggerFactory.getLogger(SellerController.class);

    @Autowired
    private JwtService jwtService;

    @GetMapping("/")
    public String home(@CookieValue(value = "jwtToken", defaultValue = "") String jwtToken) {

        if (jwtToken != null) {
            
            if (jwtToken.equals("no text") || jwtToken.equals("") || jwtToken.equals(" ")) {
                logger.info("not logged in");
            } else {
                Claims claims = jwtService.extractAllClaims(jwtToken);
                logger.info("Seller logged in: " + claims.getSubject());
            }
        } else {
            System.out.println("masuk sini kh");
            logger.info("not logged in");
        }

        return "home";
    }
}
