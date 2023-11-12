package com.apapedia.order.restservice;

import com.apapedia.order.model.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartItemService {
    List<CartItem> retrieveRestAllCartItem();
    CartItem getRestCartItemById(UUID id);
    CartItem updateRestCartItemQuantity(CartItem cartItemFromDTO);

    void deleteRestCartItem(CartItem cartItem);

}
