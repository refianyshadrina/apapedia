package com.apapedia.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.order.model.CartItem;
import com.apapedia.order.repository.CartDb;
import com.apapedia.order.repository.CartItemDb;

import jakarta.transaction.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartDb cartDb;

    @Autowired
    CartItemDb cartItemDb;

    @Override
    public CartItem createCartItem(CartItem cartItem, UUID cartId) {
        var cart = cartDb.findByCartId(cartId);
        cartItem.setCart(cart);
        cartItemDb.save(cartItem);
        return cartItem;
    };
    
}
