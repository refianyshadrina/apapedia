package com.apapedia.order.service;

import com.apapedia.order.model.CartItem;

import java.util.UUID;

public interface CartItemService {
    CartItem createCartItem(CartItem cartItem, UUID cartId);
    
}
