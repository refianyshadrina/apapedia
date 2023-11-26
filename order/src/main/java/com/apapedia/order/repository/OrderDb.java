package com.apapedia.order.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apapedia.order.model.Order;

import java.util.UUID;

@Repository
public interface OrderDb extends JpaRepository<Order, UUID> {
    
}
