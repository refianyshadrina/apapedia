package com.apapedia.order.restservice;

import com.apapedia.order.dto.UserDTO;
import com.apapedia.order.model.UserDummy;

public interface UserRestService {
    UserDummy createUserDummy(UserDummy user);

    UserDTO getUserByUsername(String username);
}
