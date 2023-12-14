package com.apapedia.order.restservice;

import com.apapedia.order.dto.CatalogDTO;
import com.apapedia.order.model.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartItemRestService {
    CartItem createCartItem(CartItem cartItem, UUID cartId);
    
    List<CartItem> retrieveRestAllCartItem();

    CartItem getRestCartItemById(UUID id);

    CartItem updateRestCartItemQuantity(CartItem cartItemFromDTO, int newQuantity);

    void deleteRestCartItem(CartItem cartItem);

    CatalogDTO retrieveCatalogbyId(UUID productId);

}
