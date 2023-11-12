package com.apapedia.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apapedia.order.model.UserDummy;

import java.util.UUID;

@Repository
public interface UserDb extends JpaRepository<UserDummy, UUID> {
    UserDummy findByUserId(UUID userId);
}
