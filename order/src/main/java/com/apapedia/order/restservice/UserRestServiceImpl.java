package com.apapedia.order.restservice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.apapedia.order.dto.UserDTO;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService {
    private final WebClient webClient;

    public UserRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://web-user:8080")
                            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .build();
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        Mono<UserDTO> response = this.webClient
                .get()
                .uri("/api/user/get-user-sso/{username}", username)
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError(),
                    clientResponse -> clientResponse.bodyToMono(String.class)
                    .flatMap(errorMessage -> Mono.error(new RuntimeException(errorMessage)))
                )
                .onStatus(
                        status -> status.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("Internal Server Error: " + clientResponse.rawStatusCode()))
                )
                .bodyToMono(UserDTO.class);

        return response.block();
    }

    @Override
    public UserDTO getUser(UUID idUser) {

        Mono<UserDTO> response = this.webClient
                .get()
                .uri("/api/user/detail/{id}", idUser)
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError(),
                    clientResponse -> clientResponse.bodyToMono(String.class)
                    .flatMap(errorMessage -> Mono.error(new RuntimeException(errorMessage)))
                )
                .onStatus(
                        status -> status.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("Internal Server Error: " + clientResponse.rawStatusCode()))
                )
                .bodyToMono(UserDTO.class);

        return response.block();

    }
    
}
