package com.apapedia.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.UserDummy;
import com.apapedia.order.repository.CartDb;
// import com.apapedia.user.service.SellerService;
import com.apapedia.order.repository.UserDb;

import jakarta.transaction.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    CartDb cartDb;
    
    @Autowired
    UserDb userDb;
    
    @Override
    public Cart createCart(UUID userId) {
        // UserDummy user = userDb.findByUserId(userId);
        Cart cart = new Cart();

        // cart.setUser(user);
        // user.setCart(cart);

        // cartDb.save(cart);
        // userDb.save(user);
        return cart;
    };
}
