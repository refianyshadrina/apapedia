package com.apapedia.user.service;

public interface UserService {
    boolean existsByUsername(String username);

    boolean existsByEmail(String username);
}
