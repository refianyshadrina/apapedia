package com.apapedia.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.order.model.CartItem;
import com.apapedia.order.repository.CartItemDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartItemDb cartItemDb;
    
    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItemDb.save(cartItem);
        return cartItem;
    };
    
}
