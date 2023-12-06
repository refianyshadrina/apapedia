package com.apapedia.order.restservice;

import com.apapedia.order.dto.UserDTO;
import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.model.UserDummy;
import com.apapedia.order.repository.CartDb;
import com.apapedia.order.repository.UserDb;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CartRestServiceImpl implements CartRestService {
    @Autowired
    CartDb cartDb;
    
    @Autowired
    UserDb userDb;

    private final WebClient webClient;

    public CartRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080")
                            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .build();
    }
    
    @Override
    public Cart createCart(UUID userId) {
        // UserDummy user = userDb.findByUserId(userId);
        Cart cart = new Cart();

        cart.setUserId(userId);
        // user.setCart(cart);

        cartDb.save(cart);
        // userDb.save(user);
        return cart;
    };

    @Override
    public Cart getRestCartById(UUID id) {
        for (Cart cart : retrieveRestAllCart()) {
            if (cart.getCartId().equals(id)) {
                return cart;
            }
        }
        return null;
    }

    @Override
    public List<Cart> retrieveRestAllCart() {
        return cartDb.findAll();
    }
 

    @Override
    public List<CartItem> retrieveRestAllCartItemByUserId(UUID userId) {
        Cart cart = cartDb.findByUserUserId(userId);
        return cart != null ? cart.getListCartItem() : Collections.emptyList();
    }

}
