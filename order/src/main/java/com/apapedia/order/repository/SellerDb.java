package com.apapedia.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apapedia.order.model.SellerDummy;

import java.util.UUID;

@Repository
public interface SellerDb extends JpaRepository<SellerDummy, UUID> {
    SellerDummy findBySellerId(UUID sellerId);
}
