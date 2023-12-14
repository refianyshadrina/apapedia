package com.apapedia.order.restservice;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.repository.CartDb;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CartRestServiceImpl implements CartRestService {
    @Autowired
    CartDb cartDb;

    
    @Override
    public Cart createCart(UUID userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cartDb.save(cart);
        return cart;
    };

    @Override
    public Cart getRestCartById(UUID id) {
        for (Cart cart : retrieveRestAllCart()) {
            if (cart.getCartId().equals(id)) {
                return cart;
            }
        }
        return null;
    }

    @Override
    public List<Cart> retrieveRestAllCart() {
        return cartDb.findAll();
    }
 
    @Override
    public List<CartItem> retrieveRestAllCartItemByUserId(UUID userId) {
        Cart cart = cartDb.findByUserId(userId);
        return cart != null ? cart.getListCartItem() : Collections.emptyList();
    }

}
