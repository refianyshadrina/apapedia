package com.apapedia.order.restservice;

import com.apapedia.order.model.CartItem;
import com.apapedia.order.repository.CartDb;
import com.apapedia.order.repository.CartItemDb;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartDb cartDb;

    @Autowired
    private CartItemDb cartItemDb;

    @Override
    public CartItem createCartItem(CartItem cartItem, UUID cartId) {
        var cart = cartDb.findByCartId(cartId);
        cartItem.setCart(cart);
        cartItemDb.save(cartItem);
        return cartItem;
    };

    @Override
    public List<CartItem> retrieveRestAllCartItem() {
        return cartItemDb.findAll();
    }

    @Override
    public CartItem getRestCartItemById(UUID id) {
        for (CartItem cartItem : retrieveRestAllCartItem()) {
            if (cartItem.getId().equals(id)) {
                return cartItem;
            }
        }
        return null;
    };

    @Override
    public CartItem updateRestCartItemQuantity(CartItem cartItemFromDTO) {
        CartItem cartItem = getRestCartItemById(cartItemFromDTO.getId());
        if (cartItem != null){
           cartItem.setQuantity(cartItemFromDTO.getQuantity());
           cartItemDb.save(cartItem);
        }
        return cartItem;
    }

    @Override
    public void deleteRestCartItem(CartItem cartItem) {
        cartItemDb.delete(cartItem);
    }

}
