package com.apapedia.user.restservice;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.apapedia.user.model.Seller;
import com.apapedia.user.model.UserModel;
import com.apapedia.user.payload.JwtResponse;
import com.apapedia.user.payload.LoginRequest;
import com.apapedia.user.payload.RegisterRequest;
import com.apapedia.user.payload.UpdateBalanceUser;
import com.apapedia.user.payload.UpdateUserRequestDTO;
import com.apapedia.user.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService{

    @Autowired
    UserService userService;

    private final WebClient webClient;

    public UserRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080")
                            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .build();
    }
    
    @Override
    public Seller getUser(UUID idUser, String jwtToken) {

        Mono<Seller> response = this.webClient
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
                .bodyToMono(Seller.class);

        return response.block();

    }

    @Override
    public void deleteUser(UUID idUser) {
        this.webClient
            .delete()
            .uri("api/user/delete/{id}", idUser)
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
    public UserModel signUp(@Valid RegisterRequest registerRequest) {

        Mono<UserModel> response = this.webClient
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
            .bodyToMono(UserModel.class);
        return response.block();
    }

    @Override
    public JwtResponse update(@Valid UpdateUserRequestDTO updateUserRequestDTO) {

        UserModel response = this.webClient
            .put()
            .uri("/api/user/update")
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
            .bodyToMono(UserModel.class).block();

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
    public UserModel updateBalance(UpdateBalanceUser updateRequest) {
        UserModel response = this.webClient
            .put()
            .uri("/api/user/self-update-balance")
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
            .bodyToMono(UserModel.class).block();

        return response;

    }

    

}
