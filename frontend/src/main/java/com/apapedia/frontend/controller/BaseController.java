package com.apapedia.frontend.controller;

import com.apapedia.frontend.payloads.CatalogDTO;
import com.apapedia.frontend.restService.CatalogRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.ui.Model;

import com.apapedia.frontend.payloads.UserDTO;
import com.apapedia.frontend.restService.UserRestService;
import com.apapedia.frontend.service.FrontEndService;
import com.apapedia.frontend.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private JwtService jwtService;

    @Qualifier("catalogRestServiceImpl")
    @Autowired
    CatalogRestService catalogRestService;

    @Autowired
    FrontEndService frontEndService;

    @Autowired
    UserRestService userRestService;

    @GetMapping("/")
    public String home(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        UserDTO userDTO = null;
        boolean isLoggedIn = false;

        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            System.out.println(userDTO);
            List<CatalogDTO> listCatalog = catalogRestService.viewAllCatalog();
            model.addAttribute("listCatalog", listCatalog);
            logger.info("This is the list, " + listCatalog);
            logger.info("not logged in");
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);
            isLoggedIn = true;
            List<CatalogDTO> listCatalog = catalogRestService.viewAllCatalogBySellerId(id, jwtToken);
            logger.info("This is the list, " + listCatalog);

            model.addAttribute("listCatalog", listCatalog);
            try {
                userDTO = userRestService.getUser(id, jwtToken);
            } catch (RuntimeException e) {
                redirectAttrs.addFlashAttribute("error", "Your session has expired. Please log in again");
                return "redirect:/logout";
            }

            logger.info("Seller logged in: " + jwtToken);
        }

        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("user", userDTO);

        return "home";
    }
}

