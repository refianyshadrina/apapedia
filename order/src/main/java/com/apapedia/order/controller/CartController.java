package com.apapedia.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.order.dto.CartItemMapper;
import com.apapedia.order.dto.CartMapper;
import com.apapedia.order.dto.request.CreateCartItemRequestDTO;
import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.service.CartItemService;
import com.apapedia.order.service.CartService;

import java.util.NoSuchElementException;
import java.util.UUID;

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
    
    @PostMapping(value = "/cart/create")
    public ResponseEntity<Cart> addCart(@RequestParam("userId") UUID userId){
        try{
            var cart = cartService.createCart(userId);
            return ResponseEntity.ok(cart);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User ID " + userId + " not found"
            );
        }
    }
    
    // @PostMapping(value = "/cart-item/create")
    // public ResponseEntity<CartItem> addCartItem(@RequestBody CreateCartItemRequestDTO cartItemDTO){
    //     try{
    //         var cartId = cartItemDTO.getCartId();
    //         var cartItem = cartItemMapper.createCartItemRequestDTOToCartItem(cartItemDTO);
    //         cartItemService.createCartItem(cartItem, cartId);
    //         return ResponseEntity.ok(cartItem);
    //     } catch (NoSuchElementException e){
    //         throw new ResponseStatusException(
    //             HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
    //         );
    //     }
    // }
}
