package com.apapedia.frontend.restService;

import java.util.UUID;

import com.apapedia.frontend.payloads.CatalogDTO;
import com.apapedia.frontend.payloads.CreateCatalogDTO;

import java.util.List;

public interface CatalogRestService {

    List<CatalogDTO> viewAllCatalog();

    List<CatalogDTO> viewAllCatalogBySellerId(UUID id, String jwtToken);

    List<CatalogDTO> searchCatalogByCatalogName(String productName);

    List<CatalogDTO> searchCatalogByCatalogNameBySellerId(UUID id, String productName, String jwtToken);

    List<CatalogDTO> sortCatalogByNameAsc();

    List<CatalogDTO> sortCatalogByNameDesc();

    List<CatalogDTO> filterCatalogByPrice(int minPrice, int maxPrice);
    List<CatalogDTO> filterCatalogByPriceBySellerId(int minPrice, int maxPrice, UUID sellerId, String jwtToken);
    List<CatalogDTO> sortCatalogByPriceAsc();

    List<CatalogDTO> sortCatalogByPriceDesc();

    CatalogDTO getCatalogById(UUID id);
    CatalogDTO getCatalogById(UUID id, String jwtToken);
    public CatalogDTO updateCatalog(CatalogDTO updatedCatalog, String jwtToken);

    void createRestCatalog(CatalogDTO catalog, String jwtToken);










    
}
