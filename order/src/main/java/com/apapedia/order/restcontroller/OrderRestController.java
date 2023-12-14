package com.apapedia.order.restcontroller;

import com.apapedia.order.dto.TotalDTO;
import com.apapedia.order.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.apapedia.order.dto.OrderDTO;
import com.apapedia.order.dto.OrderMapper;
import com.apapedia.order.dto.request.CreateOrdersRequestDTO;
import com.apapedia.order.dto.request.UpdateOrderRequestDTO;
import com.apapedia.order.restservice.OrderRestService;
import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class OrderRestController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRestService orderService;

    @PostMapping("/orders/create")
    public ResponseEntity<Order> addOrder(@Valid @RequestBody CreateOrdersRequestDTO orderDTO) {
        try {
            var order = orderMapper.CreateOrdersRequestDTOToOrder(orderDTO);
            orderService.createOrders(order);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/seller/{seller_id}")
    public ResponseEntity<?> getOrdersBySeller(@PathVariable("seller_id") UUID sellerId) {
        List<Order> orders = orderService.getOrdersBySellerId(sellerId);
        if (orders.isEmpty()) {
            String errorMessage = "Order not found for Seller ID " + sellerId;
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<?> getOrdersByCustomer(@PathVariable("customer_id") UUID customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        if (orders.isEmpty()) {
            String errorMessage = "Order not found for Customer ID " + customerId;
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @CrossOrigin("*")
    @PutMapping("/orders/update-status")
    public ResponseEntity<Order> updateOrderStatus(@Valid @RequestBody UpdateOrderRequestDTO orderDTO) {
        try {
            var order = orderMapper.updateOrderRequestDTOToOrder(orderDTO);
            orderService.updateRestOrderStatus(order);
            return ResponseEntity.ok().body(order);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id Order " + orderDTO.getId() + " not found"
            );
        }
    }

    @GetMapping("/orders/top5/{seller_id}")
    private List<TotalDTO> retrieveTopBySellerId(@PathVariable("seller_id") String sellerIdString) {
        try {
            UUID sellerId = UUID.fromString(sellerIdString);

            List<Order> allOrders = orderService.getOrdersBySellerId(sellerId);

            allOrders.removeIf(order -> order.getStatus() != 5);


            List<OrderItem> allOrderItems = new ArrayList<>();
            for (Order order : allOrders) {
                List<OrderItem> orderItems = order.getListOrderItem();
                allOrderItems.addAll(orderItems);
            }

            Map<UUID, List<OrderItem>> groupedItems = allOrderItems.stream()
                    .collect(Collectors.groupingBy(OrderItem::getProductId));

            Map<UUID, Integer> totalQuantitySold = new HashMap<>();
            for (Map.Entry<UUID, List<OrderItem>> entry : groupedItems.entrySet()) {
                int quantity = entry.getValue().stream()
                        .mapToInt(OrderItem::getQuantity)
                        .sum();
                totalQuantitySold.put(entry.getKey(), quantity);
            }

            List<OrderItem> bestSellingOrderItems = new ArrayList<>();
            totalQuantitySold.entrySet().stream()
                    .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
                    .forEach(entry -> {
                        OrderItem orderItem = groupedItems.get(entry.getKey()).get(0);
                        orderItem.setQuantity(entry.getValue());
                        bestSellingOrderItems.add(orderItem);
                    });

            List<TotalDTO> totalDTOList = new ArrayList<>();
            for (OrderItem orderItem : bestSellingOrderItems) {
                TotalDTO totalDTO = new TotalDTO();
                totalDTO.setItemName(orderItem.getProductName());
                totalDTO.setTotal(orderItem.getQuantity());
                totalDTOList.add(totalDTO);
            }

            return totalDTOList;
        } catch (IllegalArgumentException | NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No orders found"
            );
        }
    }

    @GetMapping("/orders/history/{seller_id}")
    public ResponseEntity<List<OrderDTO>> getHistory(@PathVariable("seller_id") UUID sellerId) {
        List<Order> listOrder = orderService.getOrdersBySellerId(sellerId);
        List<OrderDTO> listOrderDTO = orderService.getAllOrderDTO(listOrder);

        return ResponseEntity.ok(listOrderDTO);
    }

    @CrossOrigin("*")
    @GetMapping("/orders/historyCustomer/{customer_id}")
    public ResponseEntity<?> getHistoryCustomer(@PathVariable("customer_id") UUID customerId) {
        System.out.println(customerId);
        List<Order> listOrder = orderService.getOrdersByCustomerId(customerId);
        List<OrderDTO> listOrderDTO = orderService.getAllOrderDTO(listOrder);

        return ResponseEntity.ok(listOrderDTO);
    }
}