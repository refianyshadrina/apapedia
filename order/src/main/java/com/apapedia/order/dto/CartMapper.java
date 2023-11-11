package com.apapedia.order.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.apapedia.order.dto.request.CreateCartRequestDTO;
import com.apapedia.order.model.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "listCartItem", ignore = true)
    Cart createCartRequestDTOToCart(CreateCartRequestDTO createCartRequestDTO);
}
