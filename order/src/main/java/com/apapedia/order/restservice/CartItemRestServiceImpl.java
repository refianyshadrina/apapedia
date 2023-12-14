package com.apapedia.order.restservice;

import com.apapedia.order.dto.CatalogDTO;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.repository.CartDb;
import com.apapedia.order.repository.CartItemDb;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CartItemRestServiceImpl implements CartItemRestService {
    @Autowired
    CartDb cartDb;

    @Autowired
    private CartItemDb cartItemDb;

    @Override
    public CartItem createCartItem(CartItem cartItem, UUID cartId) {
        var cart = cartDb.findByCartId(cartId);
        
        // Check if the cartItem with the same productId and cartId already exists in the cart
        CartItem existingCartItem = cart.getListCartItem().stream()
                .filter(item -> item.getProductId().equals(cartItem.getProductId()) && item.getCart().getCartId().equals(cartId))
                .findFirst()
                .orElse(null);
    
        if (existingCartItem != null) {
            // If the cartItem already exists, update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity());
            cartItemDb.save(existingCartItem);
        } else {
            // Set the cart reference to the cartItem
            cartItem.setCart(cart);
            
            // If the cartItem doesn't exist, save the new cartItem
            cartItemDb.save(cartItem);
        }
    
        // Print the updated list of cart items
        List<CartItem> listCartItem = cart.getListCartItem();
        System.out.println("Updated Cart Items:");
        for (CartItem item : listCartItem) {
            System.out.println("Item ID: " + item.getId() + ", Quantity: " + item.getQuantity());
        }
        return cartItem;
    };

    @Override
    public List<CartItem> retrieveRestAllCartItem() {
        return cartItemDb.findAll();
    }

    @Override
    public CartItem getRestCartItemById(UUID id) {
        for (CartItem cartItem : retrieveRestAllCartItem()) {
            if (cartItem.getId().equals(id)) {
                return cartItem;
            }
        }
        return null;
    };

    @Override
    public CartItem updateRestCartItemQuantity(CartItem cartItemFromDTO, int newQuantity) {
        CartItem cartItem = getRestCartItemById(cartItemFromDTO.getId());
        if (cartItem != null){
            if (newQuantity == 0) {
                cartItemDb.delete(cartItem);
            } else {
                cartItem.setQuantity(newQuantity);
                cartItemDb.save(cartItem);
            }
        }
        return cartItem;
    }

    @Override
    public void deleteRestCartItem(CartItem cartItem) {
        cartItemDb.delete(cartItem);
    }

    
    private final WebClient webClient;

    public CartItemRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082")
                            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .build();
    }

    @Override
    public CatalogDTO retrieveCatalogbyId(UUID id) {
        CatalogDTO listCatalog = webClient
            .get()
            .uri("/api/catalog/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<CatalogDTO>() {})
            .block();
    
        return listCatalog;
    }
    
    

}
