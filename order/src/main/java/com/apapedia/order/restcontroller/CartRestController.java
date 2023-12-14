package com.apapedia.order.restcontroller;

import com.apapedia.order.dto.CartItemMapper;
import com.apapedia.order.dto.CatalogDTO;
import com.apapedia.order.dto.request.CreateCartItemRequestDTO;
import com.apapedia.order.dto.request.UpdateCartItemRequestDTO;
import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.restservice.CartItemRestService;
import com.apapedia.order.restservice.CartRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CartRestController {
    // @Autowired
    // private CartMapper cartMapper;

    @Autowired
    private CartRestService cartService;

    @Autowired(required=false)
    private CartItemMapper cartItemMapper;

    @Autowired
    private CartItemRestService cartItemService;
    
    @PostMapping(value = "/cart/create/user")
    public ResponseEntity<Cart> createCart(@RequestParam("userId") UUID userId){
        try{
            var cart = cartService.createCart(userId);
            return ResponseEntity.ok(cart);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User ID " + userId + " not found"
            );
        }
    }
    
    @PostMapping(value = "/cart-item/add")
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

    @PutMapping("/cart-item/update")
    public ResponseEntity<String> updateQuantityCartItem(@RequestBody UpdateCartItemRequestDTO cartItemDTO) {
        try {
            var cartItem = cartItemMapper.updateCartItemRequestDTOToCartItem(cartItemDTO);
            var newQuantity = cartItemDTO.getQuantity();
            cartItemService.updateRestCartItemQuantity(cartItem, newQuantity);
            return new ResponseEntity<>("Item quantity updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to update item quantity in the cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cart/view-all/{userId}")
    public List<CartItem> restGetCartItemByUserId(@PathVariable("userId") UUID userId) {
        return cartService.retrieveRestAllCartItemByUserId(userId);
    }

    @GetMapping("/cart-item/catalogDTO/{productId}")
    public CatalogDTO retrieveCatalogbyId(@PathVariable("productId") UUID productId) {
        System.out.println("masuk");
        return cartItemService.retrieveCatalogbyId(productId);
    }
    

    @DeleteMapping("/cart-item/delete/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable("id") UUID id) {
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
