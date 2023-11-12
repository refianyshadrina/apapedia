package com.apapedia.catalog.restservice;
import java.util.*;

import com.apapedia.catalog.model.Catalog;
import com.apapedia.catalog.repository.CatalogDb;

public interface CatalogService {

    void createRestCatalog(Catalog catalog);
    List<Catalog>retrieveAllCatalog();
    
    Catalog getCatalogById(UUID id);
    Catalog updateCatalog (Catalog updatedCatalog);

    List<Catalog>getAllCatalogsBySellerId(long sellerId);


}
