package com.apapedia.order.restservice;

import com.apapedia.order.dto.UserDTO;
import com.apapedia.order.model.UserDummy;

public interface UserService {
    UserDummy createUserDummy(UserDummy user);

    UserDTO getUserByUsername(String username);
}
