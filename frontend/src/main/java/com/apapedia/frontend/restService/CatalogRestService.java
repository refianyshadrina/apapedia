package com.apapedia.frontend.restService;

import java.util.UUID;

import com.apapedia.frontend.payloads.CatalogDTO;

import java.util.List;

public interface CatalogRestService {

    List<CatalogDTO> viewAllCatalog();

    List<CatalogDTO> viewAllCatalogBySellerId(UUID id);
    
}
