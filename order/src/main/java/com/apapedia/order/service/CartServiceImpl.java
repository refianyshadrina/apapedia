package com.apapedia.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.order.model.Cart;
import com.apapedia.order.repository.CartDb;
// import com.apapedia.user.service.SellerService;

import jakarta.transaction.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    CartDb cartDb;
    
    @Override
    public Cart createCart(Cart cart, UUID userId) {
        cart.setUserId(userId);
        cartDb.save(cart);
        return cart;
    };
}
