package com.apapedia.catalog.restservice;

import com.apapedia.catalog.dto.UserDTO;

public interface UserRestService {
    UserDTO getUserByUsername(String username);
}
