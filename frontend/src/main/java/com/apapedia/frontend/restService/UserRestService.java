package com.apapedia.frontend.restService;

import java.util.UUID;

import com.apapedia.frontend.payloads.JwtResponse;
import com.apapedia.frontend.payloads.LoginRequest;
import com.apapedia.frontend.payloads.UserDTO;
// import com.apapedia.user.payload.user.JwtResponseDTO;
import com.apapedia.frontend.payloads.UpdateBalanceUser;
import com.apapedia.frontend.payloads.UpdateUserRequest;

import jakarta.validation.Valid;

public interface UserRestService {
    UserDTO getUser(UUID idUser, String jwtToken);

    JwtResponse login(@Valid LoginRequest authRequest);

    UserDTO createUser(@Valid UserDTO registerRequest);

    JwtResponse update(@Valid UpdateUserRequest updateUserRequestDTO, String jwtToken);

    void deleteUser(UUID idUser, String jwtToken);

    UserDTO updateBalance(UpdateBalanceUser updateRequest, String jwtToken);

    UserDTO updateBalanceV2(UpdateBalanceUser updateRequest, String jwtToken);

    UserDTO getUserByUsername(String username);

    String getTokenForSSO(String username, String name);
}
