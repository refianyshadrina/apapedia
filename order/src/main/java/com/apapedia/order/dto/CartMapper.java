package com.apapedia.order.dto;

import org.mapstruct.Mapper;

import com.apapedia.order.model.Cart;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart createCartRequestDTOToCart(UUID cartId, UUID userId);
}
