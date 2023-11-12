package com.apapedia.catalog.restservice;

import com.apapedia.catalog.model.Catalog;
import com.apapedia.catalog.repository.CatalogDb;

import java.util.List;
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
}
