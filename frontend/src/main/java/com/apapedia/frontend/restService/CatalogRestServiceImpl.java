package com.apapedia.frontend.restService;

import java.util.List;
import java.util.UUID;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.apapedia.frontend.payloads.CatalogDTO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CatalogRestServiceImpl implements CatalogRestService{

    private final WebClient webClient;

    public CatalogRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082")
                            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .build();
    }

    @Override
    public List<CatalogDTO> viewAllCatalog() {
        List<CatalogDTO> listCatalog = webClient
                .get()
                .uri("/api/catalog/view-all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CatalogDTO>>() {})
                .block();

        return listCatalog;
    }

    @Override
    // /????????????????????????????
    public List<CatalogDTO> viewAllCatalogBySellerId(UUID id) {
                List<CatalogDTO> listCatalog = webClient
                .get()
                .uri("/api/catalog/view-all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CatalogDTO>>() {})
                .block();

        return listCatalog;
    }
    
}
