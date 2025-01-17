package com.apapedia.catalog.restservice;

import com.apapedia.catalog.dto.request.CreateCatalogRequestDTO;
import com.apapedia.catalog.model.Catalog;
import com.apapedia.catalog.model.Category;
import com.apapedia.catalog.repository.CatalogDb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogDb catalogDb;

    @Autowired
    CategoryService categoryService;
//    @Override
//    public void createRestCatalog(Catalog catalog) {
//        catalogDb.save(catalog);
//    }

    @Override
    public Catalog createRestCatalog(CreateCatalogRequestDTO catalogDTO) {
        Category category = categoryService.retrieveByCategoryId(catalogDTO.getCategory()).get();
        Catalog catalog = new Catalog();
        catalog.setSellerId(catalogDTO.getSellerId());
        catalog.setProductName(catalogDTO.getProductName());
        catalog.setProductDescription(catalogDTO.getProductDescription());
        catalog.setPrice(catalogDTO.getPrice());
        catalog.setImage(catalogDTO.getImage());
        catalog.setStock(catalogDTO.getStock());
        catalog.setCategory(category);

        catalogDb.save(catalog);

        return catalog;
    }



    @Override
    public List<Catalog> retrieveAllCatalog() {
        return catalogDb.findAllOrderByProductNameIgnoreCaseAsc();
    }

    @Override
    public Catalog getCatalogById(UUID id) {
        return catalogDb.findById(id).orElse(null);
    }


    @Override
    public Catalog updateCatalog(Catalog updatedCatalog) {
        Catalog existingCatalog = getCatalogById(updatedCatalog.getId());
        existingCatalog.setSellerId(existingCatalog.getSellerId());
        existingCatalog.setProductName(updatedCatalog.getProductName());
        existingCatalog.setPrice(updatedCatalog.getPrice());
        existingCatalog.setProductDescription(updatedCatalog.getProductDescription());
        // existingCatalog.setImage(updatedCatalog.getImage());
        existingCatalog.setCategory(updatedCatalog.getCategory());

        return catalogDb.save(existingCatalog);
    }

    @Override
    public List<Catalog> getAllCatalogsBySellerId(UUID sellerId) {
        return catalogDb.findBySellerId(sellerId);
    }

    @Override
    public List<Catalog> getCatalogListByCatalogName(String productName){
        return catalogDb.findByProductNameContainingIgnoreCase(productName);
    }

    @Override
    public List<Catalog> getCatalogListByCatalogNameBySellerId(String productName, UUID sellerId) {
        List<Catalog> filtered = catalogDb.findByProductNameContainingIgnoreCase(productName);
        List<Catalog> filtered2 = new ArrayList<>();
        for (Catalog catalog : filtered) {
            if (catalog.getSellerId().equals(sellerId)) {
                filtered2.add(catalog);
            }
        }
        return filtered2;
    }

    @Override
    public List<Catalog> getCatalogByPriceBySellerId(int minPrice, int maxPrice, UUID sellerId) {
        List<Catalog> filtered = catalogDb.findByPriceBetween(minPrice, maxPrice);
        List<Catalog> filtered2 = new ArrayList<>();
        for (Catalog catalog : filtered) {
            if (catalog.getSellerId().equals(sellerId)) {
                filtered2.add(catalog);
            }
        }
        return filtered2;
    }

    @Override
    public List<Catalog> getCatalogByPrice(int minPrice, int maxPrice) {
        return catalogDb.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public void hardDeleteCatalog(UUID id) {
        catalogDb.deleteById(id);
    }

    @Override
    public void deleteCatalog(UUID id) {

        Optional<Catalog> optionalCatalog = catalogDb.findById(id);

        if (optionalCatalog.isPresent()) {
            Catalog current = optionalCatalog.get();
            current.setDeleted(true);
            catalogDb.save(current);
        }
    }

    @Override
    public List<Catalog> getSortedCatalogList(String sortBy, String order) {
        if ("name".equalsIgnoreCase(sortBy)) {
            return "desc".equalsIgnoreCase(order) ?
                    catalogDb.findAllByOrderByProductNameDesc() :
                    catalogDb.findAllByOrderByProductNameAsc();
        } else if ("price".equalsIgnoreCase(sortBy)) {
            return "desc".equalsIgnoreCase(order) ?
                    catalogDb.findAllByOrderByPriceDesc() :
                    catalogDb.findAllByOrderByPriceAsc();
        } return catalogDb.findAllByOrderByProductNameAsc();
    }

    // versi 2 in case implementasi yang di atas ribet
    @Override
    public List<Catalog> sortByProductNameAsc() {
        return catalogDb.findAllByOrderByProductNameAsc();
    }
    @Override
    public List<Catalog> sortByProductNameDesc() {
        return catalogDb.findAllByOrderByProductNameDesc();
    }
    @Override
    public List<Catalog> sortByPriceAsc() {
        return catalogDb.findAllByOrderByPriceAsc();
    }
    @Override
    public List<Catalog> sortByPriceDesc() {
        return catalogDb.findAllByOrderByPriceDesc();
    }

}
