package com.apapedia.order.restservice;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartRestService {
    Cart createCart(UUID userId);

    Cart getRestCartById(UUID id);
    
    List<Cart> retrieveRestAllCart();

    List<CartItem> retrieveRestAllCartItemByUserId(UUID userId);
}
