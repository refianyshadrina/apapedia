package com.apapedia.order.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.order.dto.OrderMapper;
import com.apapedia.order.dto.request.CreateOrdersRequestDTO;
import com.apapedia.order.dto.request.UpdateOrderRequestDTO;
import com.apapedia.order.model.Cart;
import com.apapedia.order.model.Order;
import com.apapedia.order.model.SellerDummy;
import com.apapedia.order.model.UserDummy;
import com.apapedia.order.restservice.CartRestService;
import com.apapedia.order.restservice.OrderRestService;

import jakarta.validation.Valid;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrderRestController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRestService orderService;

    @Autowired
    private CartRestService cartService;

    @PostMapping("/orders/create")
    public Order addOrder(@Valid @RequestBody CreateOrdersRequestDTO orderDTO) {
        try {
            // UUID userId = orderDTO.getUserId().getUserId();
            // Cart carts = cartService.getRestCartById(userId);

            // if (carts == null) {
            //     throw new ResponseStatusException(
            //             HttpStatus.BAD_REQUEST, "User does not have a cart or the carts are empty"
            //     );
            // }
            var order = orderMapper.CreateOrdersRequestDTOToOrders(orderDTO);
            orderService.createOrders(order);
            return order;

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cart not found for customer with ID: " + orderDTO.getUserId()
            );
        }
    }

    @GetMapping("/orders/cust/{user_id}")
    private Order retrieveOrderbyUserId(@PathVariable("user_id") String user_id){
        try{
            UUID custId = UUID.fromString(user_id);
            UserDummy userDummy = new UserDummy(custId, user_id, null, null, null);
            return orderService.getOrdersByUserId(userDummy);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Customer with " + user_id + " not found"
            );
    
        }
    }

    @GetMapping("/orders/seller-view/{seller_id}")
    private Order retrieveOrderbySellerId(@PathVariable("seller_id") String sellerIdString) {
        try {
            UUID sellerId = UUID.fromString(sellerIdString);
            SellerDummy sellerDummy = new SellerDummy(sellerId, sellerIdString, null, null);
                return orderService.getOrdersBySellerId(sellerDummy);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Seller with ID " + sellerIdString + " not found"
            );
        }
    }
    
    @PutMapping("/orders/update-status")
    public ResponseEntity<String> updateOrderStatus(@Valid @RequestBody UpdateOrderRequestDTO orderDTO) {
        try {
            var order = orderMapper.updateOrderRequestDTOToOrder(orderDTO);
            orderService.updateRestOrderStatus(order);
            return ResponseEntity.ok("Order status updated successfully.");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id Order " + orderDTO.getId() + " not found"
            );
        }
    }
}