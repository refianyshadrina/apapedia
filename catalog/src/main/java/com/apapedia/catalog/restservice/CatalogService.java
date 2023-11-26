package com.apapedia.catalog.restservice;
import java.util.*;

import com.apapedia.catalog.model.Catalog;
import com.apapedia.catalog.repository.CatalogDb;

public interface CatalogService {

    void createRestCatalog(Catalog catalog);
    List<Catalog>retrieveAllCatalog();
    
    Catalog getCatalogById(UUID id);
    Catalog updateCatalog (Catalog updatedCatalog);

    List<Catalog>getAllCatalogsBySellerId(UUID sellerId);

    List<Catalog> getCatalogListByCatalogName(String productName);

    List<Catalog> getCatalogByPrice(int minPrice, int maxPrice);

    List<Catalog> getSortedCatalogList(String sortBy, String order);

    List<Catalog> sortByProductNameAsc();

    List<Catalog> sortByProductNameDesc();

    List<Catalog> sortByPriceAsc();

    List<Catalog> sortByPriceDesc();





    List<Catalog> getCatalogListByCatalogName(String productName);

    List<Catalog> getCatalogByPrice(int minPrice, int maxPrice);

    void hardDeleteCatalog(UUID id);

    void deleteCatalog(UUID id);

    void deleteCatalog(UUID id);

    void hardDeleteCatalog(UUID id);

    List<Catalog> getSortedCatalogList(String sortBy, String order);

    List<Catalog> sortByProductNameAsc();

    List<Catalog> sortByProductNameDesc();

    List<Catalog> sortByPriceAsc();

    List<Catalog> sortByPriceDesc();

}
