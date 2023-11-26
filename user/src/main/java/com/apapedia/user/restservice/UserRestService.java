package com.apapedia.user.restservice;

import java.util.UUID;

import com.apapedia.user.payload.frontend.JwtResponse;
import com.apapedia.user.payload.frontend.LoginRequest;
import com.apapedia.user.payload.frontend.UserDTO;
// import com.apapedia.user.payload.user.JwtResponseDTO;
import com.apapedia.user.payload.frontend.UpdateBalanceUser;
import com.apapedia.user.payload.frontend.UpdateUserRequest;

import jakarta.validation.Valid;

public interface UserRestService {
    UserDTO getUser(UUID idUser, String jwtToken);

    JwtResponse login(@Valid LoginRequest authRequest);

    UserDTO signUp(@Valid UserDTO registerRequest);

    JwtResponse update(@Valid UpdateUserRequest updateUserRequestDTO);

    void deleteUser(UUID idUser);

    UserDTO updateBalance(UpdateBalanceUser updateRequest);
}
