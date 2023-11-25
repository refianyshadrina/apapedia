package com.apapedia.order.restservice;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartService {
    Cart createCart(UUID userId);
    
    List<CartItem> retrieveRestAllCartItemByUserId(UUID userId);
}
