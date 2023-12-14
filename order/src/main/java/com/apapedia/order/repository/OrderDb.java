package com.apapedia.order.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apapedia.order.model.Order;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderDb extends JpaRepository<Order, UUID> {
    List<Order> findBySellerId(UUID id);
    List<Order> findByCustomerId(UUID id);
}
