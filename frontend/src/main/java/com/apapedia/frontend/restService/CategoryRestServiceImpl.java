package com.apapedia.frontend.restService;

import com.apapedia.frontend.payloads.CatalogDTO;
import com.apapedia.frontend.payloads.CategoryDTO;
import jakarta.transaction.Transactional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryRestServiceImpl implements CategoryRestService{

    private final WebClient webClient;

    public CategoryRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://web-catalog:8082")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public List<CategoryDTO> retrieveAllCategories() {
        List<CategoryDTO> listCategory = webClient
                .get()
                .uri("/api/categories")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CategoryDTO>>() {})
                .block();
        return listCategory;
    }

    @Override
    public CategoryDTO getCategoryById(UUID categoryId) {
        return webClient.get()
                .uri("/api/categories/{id}", categoryId)
                .retrieve()
                .bodyToMono(CategoryDTO.class)
                .block();
    }
}
