package com.apapedia.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apapedia.user.model.Seller;


@Repository
public interface SellerDb extends JpaRepository<Seller, UUID>{
// public interface SellerDb extends JpaRepository<Seller, Long>{

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Seller findByUsername(String username);
}

