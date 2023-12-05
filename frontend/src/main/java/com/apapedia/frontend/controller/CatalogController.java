package com.apapedia.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.LoggerFactory;
import java.util.UUID;
import org.slf4j.Logger;

import com.apapedia.frontend.payloads.CatalogDTO;
import com.apapedia.frontend.restService.CatalogRestService;
import com.apapedia.frontend.service.FrontEndService;
import com.apapedia.frontend.service.JwtService;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;


@Controller
// seller controller
public class CatalogController {

    Logger logger = LoggerFactory.getLogger(CatalogController.class);


    @Qualifier("catalogRestServiceImpl")
    @Autowired
    CatalogRestService catalogRestService;

    @Autowired
    FrontEndService frontEndService;

    @Autowired
    JwtService jwtService;

    @GetMapping("/view-all-catalog")
    private String viewAllCatalog(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            List<CatalogDTO> listCatalog = catalogRestService.viewAllCatalog();
            model.addAttribute("listCatalog", listCatalog);
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);
            List<CatalogDTO> listCatalog = catalogRestService.viewAllCatalogBySellerId(id);
            model.addAttribute("listCatalog", listCatalog);
        }
        return "home";
    }


}

