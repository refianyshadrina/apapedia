package com.apapedia.order.restservice;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.repository.CartDb;
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

    @Override
    public List<CartItem> retrieveRestAllCartItemByUserId(UUID userId) {
        Cart cart = cartDb.findByUserId(userId);
        return cart != null ? cart.getListCartItem() : Collections.emptyList();
    }

}
