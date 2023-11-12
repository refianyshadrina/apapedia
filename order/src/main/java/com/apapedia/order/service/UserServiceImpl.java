package com.apapedia.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.order.model.UserDummy;
import com.apapedia.order.repository.UserDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDb userDb;

    @Override
    public UserDummy createUserDummy(UserDummy user) {
        userDb.save(user);
        return user;
    }
    
}
