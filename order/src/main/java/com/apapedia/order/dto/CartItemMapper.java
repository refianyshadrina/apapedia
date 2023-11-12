package com.apapedia.order.dto;

import org.mapstruct.Mapper;

import com.apapedia.order.dto.request.UpdateCartItemRequestDTO;

import com.apapedia.order.dto.request.CreateCartItemRequestDTO;
import com.apapedia.order.model.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem updateCartItemRequestDTOToCartItem(UpdateCartItemRequestDTO updateCartItemRequestDTO);

    UpdateCartItemRequestDTO cartItemToUpdateCartItemRequestDTO(CartItem cartItem);

    CartItem createCartItemRequestDTOToCartItem(CreateCartItemRequestDTO createCartItemRequestDTO);

}
