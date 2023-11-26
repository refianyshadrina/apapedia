package com.apapedia.order.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.order.dto.OrderMapper;
import com.apapedia.order.dto.request.CreateOrderRequestDTO;
import com.apapedia.order.model.Cart;
import com.apapedia.order.model.Order;
import com.apapedia.order.restservice.CartService;
import com.apapedia.order.restservice.OrderService;

import jakarta.validation.Valid;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrderRestController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @PostMapping("/orders/create")
    public Order addOrder(@Valid @RequestBody CreateOrderRequestDTO orderDTO) {
        try {
            // UUID userId = orderDTO.getUserId().getUserId();
            // Cart carts = cartService.getRestCartById(userId);

            // if (carts == null) {
            //     throw new ResponseStatusException(
            //             HttpStatus.BAD_REQUEST, "User does not have a cart or the carts are empty"
            //     );
            // }
            var order = orderMapper.CreateOrderRequestDTOToOrder(orderDTO);
            orderService.createOrder(order);
            return order;

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cart not found for customer with ID: " + orderDTO.getUserId()
            );
        }
    }

    // @GetMapping(value="/order/{id}")
    // private Order retrieveOrderbyUserId(@PathVariable("user_id") UserDummy user_id){
    //     try{
    //         return orderService.getOrderByUserId(user_id);
    //     } catch (NoSuchElementException e){
    //         throw new ResponseStatusException(
    //             HttpStatus.NOT_FOUND, "Customer with " + user_id + " not found"
    //         );
    
    //     }
    // }

    // @GetMapping(value="/order/{id}")
    // private Order retrieveOrderbySellerId(@PathVariable("seller_id") SellerDummy seller_id){
    //     try{
    //         return orderService.getOrderBySellerId(seller_id);
    //     } catch (NoSuchElementException e){
    //         throw new ResponseStatusException(
    //             HttpStatus.NOT_FOUND, "Customer with " + seller_id + " not found"
    //         );
    
    //     }
    // }

}