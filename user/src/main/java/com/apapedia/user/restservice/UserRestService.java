package com.apapedia.user.restservice;

import java.util.UUID;

import com.apapedia.user.model.Seller;
import com.apapedia.user.model.UserModel;
import com.apapedia.user.payload.JwtResponse;
import com.apapedia.user.payload.LoginRequest;
import com.apapedia.user.payload.RegisterRequest;
import com.apapedia.user.payload.UpdateBalanceUser;
import com.apapedia.user.payload.UpdateUserRequestDTO;

import jakarta.validation.Valid;

public interface UserRestService {
    Seller getUser(UUID idUser, String jwtToken);

    JwtResponse login(@Valid LoginRequest authRequest);

    UserModel signUp(@Valid RegisterRequest registerRequest);

    JwtResponse update(@Valid UpdateUserRequestDTO updateUserRequestDTO);

    void deleteUser(UUID idUser);

    UserModel updateBalance(UpdateBalanceUser updateRequest);
}
