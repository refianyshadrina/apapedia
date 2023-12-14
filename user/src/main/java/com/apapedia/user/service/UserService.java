package com.apapedia.user.service;

import java.util.List;
import java.util.UUID;

import com.apapedia.user.model.UserModel;
import com.apapedia.user.payload.user.UpdateUserRequestDTO;

public interface UserService {
    boolean existsByUsername(String username);

    boolean existsByEmail(String username);

    UserModel deleteUser(UserModel user);

    UserModel getUserById(UUID id);

    UserModel getUserByUsername(String username);

    List<UserModel> getAllUser();

    boolean isEmailExistsById(String email, UUID id);

    boolean isUsernameExistsById(String username, UUID id);

    UserModel update(UpdateUserRequestDTO updateUserRequestDTO);

    void updateBalance(UUID id, long balance);

    boolean checkAccountExists(String username);

    void updateBalanceV2(UUID id, long newBalance);
}
