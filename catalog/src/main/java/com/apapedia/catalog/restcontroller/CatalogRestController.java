package com.apapedia.catalog.restcontroller;

import com.apapedia.catalog.restservice.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

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
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
public class CatalogRestController {

    @Autowired
    CatalogService catalogService;
    
    @PostMapping(value = "/catalog")
    private Catalog createCatalog(@Valid @RequestBody CreateCatalogRequestDTO catalogRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field");

        }else{
            return catalogService.createRestCatalog(catalogRequestDTO);
        }
    }

    @GetMapping(value = "/catalog/view-all")
    public List<Catalog> retrieveAllCatalog() {
        return catalogService.retrieveAllCatalog();
    }
    
    @GetMapping(value="/catalog/{id}")
    private CreateCatalogRequestDTO retrieveCatalogbyId(@PathVariable("id") UUID id){
        try{
            Catalog catalog = catalogService.getCatalogById(id);
            CreateCatalogRequestDTO catalogDTO = new CreateCatalogRequestDTO();
            catalogDTO.setSellerId(catalog.getSellerId());
            catalogDTO.setProductName(catalog.getProductName());
            catalogDTO.setProductDescription(catalog.getProductDescription());
            catalogDTO.setPrice(catalog.getPrice());
            catalogDTO.setStock(catalog.getStock());
            catalogDTO.setImage(catalog.getImage());
            catalogDTO.setCategory(catalog.getCategory().getCategoryId());
            return catalogDTO;
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

        // var updatedCatalog = catalogMapper.UpdateCatalogRequestDTOToCatalog(updateCatalogRequestDTO);
        Catalog updatedCatalog = new Catalog();
        updatedCatalog.setSellerId(updateCatalogRequestDTO.getSellerId());
        updatedCatalog.setProductName(updateCatalogRequestDTO.getProductName());
        updatedCatalog.setProductDescription(updateCatalogRequestDTO.getProductDescription());
        updatedCatalog.setStock(updateCatalogRequestDTO.getStock());
        updatedCatalog.setImage(updateCatalogRequestDTO.getImage());

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

    @DeleteMapping("/catalog/{id}")
    public ResponseEntity<String> deleteCatalogById(@PathVariable("catalogId") UUID id) {
        try {
            catalogService.deleteCatalog(id);
            return new ResponseEntity<>("Delete Successful", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Catalog to be deleted found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/catalog/top")
    public List<Catalog> retrieveCatalogBySellerId(@RequestParam(value = "sellerId") UUID sellerId){
        return catalogService.getAllCatalogsBySellerId(sellerId);
    }

    @GetMapping("/catalog/top/search")
    public List<Catalog> searchCatalogByNameBySellerId(@RequestParam(value = "sellerId") UUID sellerId,
                                                       @RequestParam(value = "query") String productName){
        return catalogService.getCatalogListByCatalogNameBySellerId(productName, sellerId);
    }

    @GetMapping("catalog/top/filter")
    public List<Catalog> filterCatalogByPrice(@RequestParam(value = "sellerId") UUID sellerId,
                                              @RequestParam (value = "max", required = false) int maxPrice,
                                              @RequestParam(value = "min", required = false) int minPrice){
        return catalogService.getCatalogByPriceBySellerId(minPrice, maxPrice, sellerId); }

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
