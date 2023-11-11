package com.apapedia.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import com.apapedia.order.model.Cart;

@Repository
public interface CartDb extends JpaRepository<Cart, UUID> {
    
}
