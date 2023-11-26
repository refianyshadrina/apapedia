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

    UserDTO signUp(@Valid UserDTO registerRequest);

    JwtResponse update(@Valid UpdateUserRequest updateUserRequestDTO);

    void deleteUser(UUID idUser);

    UserDTO updateBalance(UpdateBalanceUser updateRequest);
}
