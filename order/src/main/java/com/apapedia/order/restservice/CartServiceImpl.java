package com.apapedia.order.restservice;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.model.UserDummy;
import com.apapedia.order.repository.CartDb;
import com.apapedia.order.repository.UserDb;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    CartDb cartDb;
    
    @Autowired
    UserDb userDb;
    
    @Override
    public Cart createCart(UUID userId) {
        UserDummy user = userDb.findByUserId(userId);
        Cart cart = new Cart();

        cart.setUser(user);
        user.setCart(cart);

        cartDb.save(cart);
        userDb.save(user);
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
        Cart cart = cartDb.findByUserUserId(userId);
        return cart != null ? cart.getListCartItem() : Collections.emptyList();
    }

}
