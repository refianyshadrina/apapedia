package com.apapedia.frontend.controller;

import com.apapedia.frontend.payloads.CatalogDTO;
import com.apapedia.frontend.payloads.CategoryDTO;
import com.apapedia.frontend.payloads.UpdateUserRequest;
import com.apapedia.frontend.payloads.UserDTO;
import com.apapedia.frontend.restService.CategoryRestService;
import com.apapedia.frontend.restService.UserRestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.LoggerFactory;
import java.util.UUID;
import org.slf4j.Logger;

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

    @Qualifier("categoryRestServiceImpl")
    @Autowired
    CategoryRestService categoryRestService;

    @Autowired
    FrontEndService frontEndService;

    @Autowired
    JwtService jwtService;

        @Autowired
    UserRestService userRestService;


    @GetMapping("/catalog")
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

    @GetMapping("/view-all-catalog")
    private String viewAllCatalog(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            List<CatalogDTO> listCatalog = catalogRestService.viewAllCatalog();
            model.addAttribute("listCatalog", listCatalog);
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);
            List<CatalogDTO> listCatalog = catalogRestService.viewAllCatalogBySellerId(id, jwtToken);
            model.addAttribute("listCatalog", listCatalog);
        }
        return "home";
    }

    @GetMapping("/product/{id}")
    private String viewProductById(@PathVariable UUID id, Model model,
                                  @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken,
                                   HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
                CatalogDTO catalogDTO = catalogRestService.getCatalogById(id);
                model.addAttribute("catalog", catalogDTO);
        }
        return "detail-product";
    }

    @GetMapping("/search")
    public String searchCatalog(@RequestParam(value = "query") String query, Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            List<CatalogDTO> searchResults = catalogRestService.searchCatalogByCatalogName(query);
            model.addAttribute("listCatalog", searchResults);
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);
            List<CatalogDTO> searchResults = catalogRestService.searchCatalogByCatalogNameBySellerId(id, query, jwtToken);
            model.addAttribute("listCatalog", searchResults);
        }
        return "home";
    }

    @GetMapping("/filter")
    public String filterCatalog(@RequestParam(value = "min") String min, @RequestParam(value = "max") String max, Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            List<CatalogDTO> filteredResults = catalogRestService.filterCatalogByPrice(Integer.parseInt(min), Integer.parseInt(max));
            model.addAttribute("listCatalog", filteredResults);
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);
            List<CatalogDTO> searchResults = catalogRestService.filterCatalogByPriceBySellerId(Integer.parseInt(min), Integer.parseInt(max), id, jwtToken);
            model.addAttribute("listCatalog", searchResults);
        }
        return "home";
    }

    @GetMapping("/sort")
    public String sortCatalog(@RequestParam(value = "sort") String sort, Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletRequest request) {
        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            List<CatalogDTO> sortedResults;
            switch (sort) {
                case "az" -> sortedResults = catalogRestService.sortCatalogByNameAsc();
                case "za" -> sortedResults = catalogRestService.sortCatalogByNameDesc();
                case "cheapest" -> sortedResults = catalogRestService.sortCatalogByPriceAsc();
                case "expensive" -> sortedResults = catalogRestService.sortCatalogByPriceDesc();
                default ->
                    // Handle default case, maybe sort by name ascending or return unsorted list
                        sortedResults = catalogRestService.sortCatalogByNameAsc();
            }
            model.addAttribute("listCatalog", sortedResults);
        }
        return "home";
    }

    @GetMapping("/add-product")
    private String formAddProduct(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken) {
        UUID sellerId = jwtService.getIdFromJwtToken(jwtToken);
        List<CategoryDTO> categories = categoryRestService.retrieveAllCategories();
        model.addAttribute("categories", categories);
        CatalogDTO catalogDTO = new CatalogDTO();
        catalogDTO.setSellerId(sellerId); // Set the seller ID
        model.addAttribute("catalogDTO", catalogDTO);

        return "create-catalog";
    }


    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("catalogDTO") CatalogDTO catalogDTO,
                             @CookieValue(value = "jwtToken") String jwtToken,
                             RedirectAttributes redirectAttributes, Model model) {
        try {
            UUID sellerId = jwtService.getIdFromJwtToken(jwtToken);
            catalogDTO.setSellerId(sellerId);
            catalogRestService.createRestCatalog(catalogDTO, jwtToken);
            redirectAttributes.addFlashAttribute("success", "Product added successfully!");
            return "redirect:/"; // Return the form view
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add product.");
            return "redirect:/"; // Return the form view with an error message
        }
    }

    @GetMapping("/edit-product/{id}")
    public String formEditProduct(@PathVariable UUID id, Model model,
                                  @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken) {
        try {
            // Fetch the product details by ID
            CatalogDTO catalogDTO = catalogRestService.getCatalogById(id, jwtToken);
            System.out.println("Category: " + catalogDTO.getCategory());
            // Fetch categories for the dropdown
            List<CategoryDTO> categories = categoryRestService.retrieveAllCategories();

            // Add fetched product details to the model
            model.addAttribute("catalogDTO", catalogDTO);
            model.addAttribute("categories", categories);
            System.out.println("All functionality works");

            return "edit-catalog";
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching product details.");
            return "redirect:/"; // Redirect to a suitable error handling page or home
        }
    }



}


