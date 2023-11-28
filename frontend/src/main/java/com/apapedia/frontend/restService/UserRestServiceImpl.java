package com.apapedia.frontend.restService;

import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.apapedia.frontend.payloads.JwtResponse;
import com.apapedia.frontend.payloads.LoginRequest;
import com.apapedia.frontend.payloads.UserDTO;
import com.apapedia.frontend.payloads.UpdateBalanceUser;
import com.apapedia.frontend.payloads.UpdateUserRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService{

    private final WebClient webClient;

    public UserRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080")
                            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .build();
    }
    
    @Override
    public UserDTO getUser(UUID idUser, String jwtToken) {

        Mono<UserDTO> response = this.webClient
                .get()
                .uri("/api/user/detail/{id}", idUser)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        clientResponse -> Mono.error(new RuntimeException("Bad Request: " + clientResponse.rawStatusCode()))
                )
                .onStatus(
                        status -> status.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("Internal Server Error: " + clientResponse.rawStatusCode()))
                )
                .bodyToMono(UserDTO.class);

        return response.block();

    }

    @Override
    public void deleteUser(UUID idUser, String jwtToken) {
        this.webClient
            .delete()
            .uri("api/user/delete/{id}", idUser)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }


    // service di frontend
    @Override
    public JwtResponse login(LoginRequest authRequest) {

        Mono<JwtResponse> response = this.webClient
            .post()
            .uri("/api/user/v2/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(authRequest)
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
            .bodyToMono(JwtResponse.class);
        return response.block();
    }


    @Override
    public UserDTO signUp(@Valid UserDTO registerRequest) {

        Mono<UserDTO> response = this.webClient
            .post()
            .uri("/api/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(registerRequest)
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
    public JwtResponse update(@Valid UpdateUserRequest updateUserRequestDTO, String jwtToken) {

        UserDTO response = this.webClient
            .put()
            .uri("/api/user/update")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(updateUserRequestDTO)
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
            .bodyToMono(UserDTO.class).block();

        LoginRequest authRequest = new LoginRequest(updateUserRequestDTO.getUsername(), response.getPassword());

        JwtResponse jwt = this.webClient.post()
            .uri("/api/user/generate-new-token")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(authRequest)
            .retrieve()
            .bodyToMono(JwtResponse.class)
            .block();

        return jwt;
    }

    @Override
    public UserDTO updateBalance(UpdateBalanceUser updateRequest, String jwtToken) {
        UserDTO response = this.webClient
            .put()
            .uri("/api/user/self-update-balance")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(updateRequest)
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
            .bodyToMono(UserDTO.class).block();

        return response;

    }

    

}

