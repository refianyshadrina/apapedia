package com.apapedia.catalog.restservice;

import com.apapedia.catalog.model.Catalog;
import com.apapedia.catalog.repository.CatalogDb;

import java.awt.*;
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

    @Override
    public void createRestCatalog(Catalog catalog) {
        catalogDb.save(catalog);
    }

    @Override
    public List<Catalog> retrieveAllCatalog() {
        return catalogDb.findAllOrderByProductNameIgnoreCaseAsc();
    }

    @Override
    public Catalog getCatalogById(UUID id) {
        for (Catalog catalog : retrieveAllCatalog()) {
            if (catalog.getId().equals(id)) {
                return catalog;
            }
        }
        return null;
    }


    @Override
    public Catalog updateCatalog(Catalog updatedCatalog) {
        Catalog existingCatalog = getCatalogById(updatedCatalog.getId());
        existingCatalog.setProductName(updatedCatalog.getProductName());
        existingCatalog.setPrice(updatedCatalog.getPrice());
        existingCatalog.setProductDescription(updatedCatalog.getProductDescription());
        existingCatalog.setImage(updatedCatalog.getImage());
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
