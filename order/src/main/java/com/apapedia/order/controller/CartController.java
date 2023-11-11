package com.apapedia.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.order.dto.CartItemMapper;
import com.apapedia.order.dto.CartMapper;
import com.apapedia.order.dto.request.CreateCartItemRequestDTO;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.service.CartItemService;
import com.apapedia.order.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private CartItemService cartItemService;
    
    @PostMapping(value = "/cart-item/create")
    public CartItem addCartItem(@Valid @RequestBody CreateCartItemRequestDTO cartItemDTO, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
        );
        } else{
            var cartItem = cartItemMapper.createCartItemRequestDTOToCartItem(cartItemDTO);
            cartItemService.createCartItem(cartItem);
            return cartItem;
        }
    }
}
