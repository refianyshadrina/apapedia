package com.apapedia.frontend.restService;

import java.util.List;
import java.util.UUID;

import com.apapedia.frontend.payloads.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.apapedia.frontend.payloads.CatalogDTO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CatalogRestServiceImpl implements CatalogRestService{

    private final WebClient webClient;

    public CatalogRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://web-catalog:8082")
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
    public List<CatalogDTO> viewAllCatalogBySellerId(UUID id, String jwtToken) {
        List<CatalogDTO> listCatalogSeller = webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/catalog/top")
                        .queryParam("sellerId", id.toString()) // Adding the sellerId as a request parameter
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Client error: " + clientResponse.statusCode()))
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("Server error: " + clientResponse.statusCode()))
                )
                .bodyToMono(new ParameterizedTypeReference<List<CatalogDTO>>() {})
                .block();

        return listCatalogSeller;
    }


    @Override
    // haven't tested this
    public List<CatalogDTO> searchCatalogByCatalogName(String productName) {
        List<CatalogDTO> listCatalogByName = webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/catalog/search")
                        .queryParam("query", productName)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CatalogDTO>>() {})
                .block();

        return listCatalogByName;
    }

    @Override
    // haven't tested this
    public List<CatalogDTO> searchCatalogByCatalogNameBySellerId(UUID sellerId, String productName, String jwtToken) {
        List<CatalogDTO> listCatalogByName = webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/catalog/top/search")
                        .queryParam("sellerId", sellerId.toString())
                        .queryParam("query", productName)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CatalogDTO>>() {})
                .block();

        return listCatalogByName;
    }


    @Override
    // haven't tested this
    public List<CatalogDTO> sortCatalogByNameAsc() {
        List<CatalogDTO> listCatalogSortedByNameAsc = webClient
                .get()
                .uri("/api/catalog/sort/name/asc")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CatalogDTO>>() {})
                .block();

        return listCatalogSortedByNameAsc;
    }

    @Override
    // haven't tested this
    public List<CatalogDTO> sortCatalogByNameDesc() {
        List<CatalogDTO> listCatalogSortedByNameDesc = webClient
                .get()
                .uri("/api/catalog/sort/name/desc")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CatalogDTO>>() {})
                .block();

        return listCatalogSortedByNameDesc;
    }

    @Override
    // haven't tested
    public List<CatalogDTO> filterCatalogByPrice(int minPrice, int maxPrice) {
        List<CatalogDTO> listCatalogByPrice = webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/catalog/filter")
                        .queryParam("min", minPrice)
                        .queryParam("max", maxPrice)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CatalogDTO>>() {})
                .block();

        return listCatalogByPrice;
    }

    @Override
    // haven't tested
    public List<CatalogDTO> filterCatalogByPriceBySellerId(int minPrice, int maxPrice, UUID sellerId, String jwtToken) {
        List<CatalogDTO> listCatalogByPrice = webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/catalog/top/filter")
                        .queryParam("sellerId", sellerId.toString())
                        .queryParam("min", minPrice)
                        .queryParam("max", maxPrice)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CatalogDTO>>() {})
                .block();

        return listCatalogByPrice;
    }


    @Override
    // haven't tested
    public List<CatalogDTO> sortCatalogByPriceAsc() {
        List<CatalogDTO> listCatalogSortedByPriceAsc = webClient
                .get()
                .uri("/api/catalog/sort/price/asc")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CatalogDTO>>() {})
                .block();

        return listCatalogSortedByPriceAsc;
    }

    @Override
    // haven't tested
    public List<CatalogDTO> sortCatalogByPriceDesc() {
        List<CatalogDTO> listCatalogSortedByPriceDesc = webClient
                .get()
                .uri("/api/catalog/sort/price/desc")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CatalogDTO>>() {})
                .block();

        return listCatalogSortedByPriceDesc;
    }

    @Override
    public CatalogDTO getCatalogById(UUID id) {
        CatalogDTO catalogById = webClient
                .get()
                .uri("/api/catalog/{id}", id.toString())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CatalogDTO>() {})
                .block();

        return catalogById;
    }

    @Override
    public CatalogDTO getCatalogById(UUID id, String jwtToken) {
        CatalogDTO catalogById = webClient
                .get()
                .uri("/api/catalog/{id}", id.toString())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CatalogDTO>() {})
                .block();

        return catalogById;
    }

    @Override
    public CatalogDTO updateCatalog(CatalogDTO updatedCatalog, String jwtToken) {
        CatalogDTO updateCatalog = webClient
                .put()
                .uri("/api/catalog")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updatedCatalog))
                .retrieve()
                .bodyToMono(CatalogDTO.class)
                .block();

        return updateCatalog;
    }

    @Override
    public void createRestCatalog(CatalogDTO catalogDTO, String jwtToken) {

            CatalogDTO createCatalog = webClient
                    .post()
                    .uri("/api/catalog")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(catalogDTO))
                    .retrieve()
                    .bodyToMono(CatalogDTO.class)
                    .block();
        }
    }


