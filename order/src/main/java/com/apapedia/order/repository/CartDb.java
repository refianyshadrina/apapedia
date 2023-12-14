package com.apapedia.order.repository;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartDb extends JpaRepository<Cart, UUID> {
    List<CartItem> findAllByUserId(UUID userId);

    Cart findByUserId(UUID userId);

    Cart findByCartId(UUID cartId);
}
