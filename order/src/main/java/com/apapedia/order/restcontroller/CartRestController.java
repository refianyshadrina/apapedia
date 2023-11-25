package com.apapedia.order.restcontroller;

import com.apapedia.order.dto.CartItemMapper;
import com.apapedia.order.dto.CartMapper;
import com.apapedia.order.dto.request.CreateCartItemRequestDTO;
import com.apapedia.order.dto.request.UpdateCartItemRequestDTO;
import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.restservice.CartItemService;
import com.apapedia.order.restservice.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CartRestController {
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
    
    @PostMapping(value = "/cart-item/create")
    public ResponseEntity<CartItem> addCartItem(@RequestBody CreateCartItemRequestDTO cartItemDTO){
        try{
            var cartId = cartItemDTO.getCartId();
            var cartItem = cartItemMapper.createCartItemRequestDTOToCartItem(cartItemDTO);
            cartItemService.createCartItem(cartItem, cartId);
            return ResponseEntity.ok(cartItem);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        }
    }

    @PutMapping("/cart-item")
    public CartItem restUpdateQuantityCartItem(@Valid @RequestBody UpdateCartItemRequestDTO cartItemDTO,

                                               BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            var cartItem = cartItemMapper.updateCartItemRequestDTOToCartItem(cartItemDTO);
            cartItemService.updateRestCartItemQuantity(cartItem);
            return cartItem;
        }
    }

    @GetMapping("/cart/view-all/{userId}")
    public List<CartItem> restGetCartItemByUserId(@PathVariable("userId") UUID userId) {
        return cartService.retrieveRestAllCartItemByUserId(userId);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity deleteCartItem(@PathVariable("id") UUID id) {
        try {
            var cartItem = cartItemService.getRestCartItemById(id);
            cartItemService.deleteRestCartItem(cartItem);
            return ResponseEntity.ok("Cart Item has been deleted");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cart Item cannot be found!"
            );
        }
    }
}
