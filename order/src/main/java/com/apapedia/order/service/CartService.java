package com.apapedia.order.service;

import java.util.UUID;

import com.apapedia.order.model.Cart;

public interface CartService {
    
    Cart createCart(UUID userId);
    
}
