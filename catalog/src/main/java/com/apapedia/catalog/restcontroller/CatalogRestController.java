package com.apapedia.catalog.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import com.apapedia.catalog.dto.CatalogMapper;
import com.apapedia.catalog.dto.request.CreateCatalogRequestDTO;
import com.apapedia.catalog.dto.request.UpdateCatalogRequestDTO;
import com.apapedia.catalog.model.Catalog;
import com.apapedia.catalog.model.Category;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.catalog.restservice.CatalogService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
public class CatalogRestController {

    @Autowired
    CatalogService catalogService;

    @Autowired
    private CatalogMapper catalogMapper;
    
    @PostMapping(value = "/catalog")
    private Catalog createCatalog(@Valid @RequestBody CreateCatalogRequestDTO CatalogRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field");

        }else{
            var catalog = catalogMapper.CreateCatalogRequestDTOToCatalog(CatalogRequestDTO);
            catalogService.createRestCatalog(catalog);
            return catalog;    
        }
    }

    @GetMapping(value = "/catalog/view-all")
    public List<Catalog> retrieveAllCatalog() {
        return catalogService.retrieveAllCatalog();
    }
    
    @GetMapping(value="/catalog/{id}")
    private Catalog retrieveCatalogbyId(@PathVariable("id") UUID id){
        try{
            return catalogService.getCatalogById(id);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Customer with " + id + " not found"
            );
    
        }
    }

    @PutMapping(value = "/catalog")
    private Catalog updateCatalog( @Valid @RequestBody UpdateCatalogRequestDTO updateCatalogRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field");
        }

        var updatedCatalog = catalogMapper.UpdateCatalogRequestDTOToCatalog(updateCatalogRequestDTO);
        return catalogService.updateCatalog(updatedCatalog);
    }

    @GetMapping("/catalog/search")
    public List<Catalog> retrieveCatalogListByCatalogName(@RequestParam(value = "query") String productName){
        return catalogService.getCatalogListByCatalogName(productName);
    }

    @GetMapping("catalog/filter")
    public List<Catalog> filterCatalogByPrice(@RequestParam (value = "max", required = false) int maxPrice,
                                              @RequestParam(value = "min", required = false) int minPrice){
        return catalogService.getCatalogByPrice(minPrice, maxPrice); }

    @GetMapping("/catalog")
    public List<Catalog> getSortedCatalogList(
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order) {
        return catalogService.getSortedCatalogList(sortBy, order);
    }

    @GetMapping("/catalog/sort/name/asc")
    public List<Catalog> sortByProductNameAsc() {
        return catalogService.sortByProductNameAsc();
    }

    @GetMapping("/catalog/sort/name/desc")
    public List<Catalog> sortByProductNameDesc() {
        return catalogService.sortByProductNameAsc();
    }

    @GetMapping("/catalog/sort/price/asc")
    public List<Catalog> sortByPriceAsc() {
        return catalogService.sortByPriceAsc();
    }

    @GetMapping("/catalog/sort/price/desc")
    public List<Catalog> sortByPriceDesc() {
        return catalogService.sortByPriceDesc();
    }




}
