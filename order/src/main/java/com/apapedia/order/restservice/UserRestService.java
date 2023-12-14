package com.apapedia.order.restservice;

import com.apapedia.order.dto.UserDTO;

import java.util.UUID;

public interface UserRestService {
    UserDTO getUserByUsername(String username);

    UserDTO getUser(UUID idUser);
}
