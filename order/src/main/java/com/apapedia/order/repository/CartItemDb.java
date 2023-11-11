package com.apapedia.order.repository;

import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apapedia.order.model.CartItem;

@Repository
public interface CartItemDb extends JpaRepository<CartItem, UUID> {
    
}
