package com.apapedia.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.apapedia.user.config.jwt.JwtService;
import com.apapedia.user.service.FrontEndService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    FrontEndService frontEndService;

    @GetMapping("/")
    public String home(@CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("not logged in");
        } else {
            Claims claims = jwtService.extractAllClaims(jwtToken);
            logger.info("Seller logged in: " + claims.getSubject());
        }

        return "home";
    }
}
