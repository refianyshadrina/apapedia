package com.apapedia.order.restservice;

import com.apapedia.order.dto.CatalogDTO;
import com.apapedia.order.dto.OrderDTO;

import com.apapedia.order.dto.OrderItemDTO;
import com.apapedia.order.model.Order;
import com.apapedia.order.model.OrderItem;
import com.apapedia.order.repository.OrderDb;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderRestServiceImpl implements OrderRestService {
    
    @Autowired
    OrderDb orderDb;

    @Autowired
    UserRestService userRestService;

    @Override
    public Order createOrders(Order orderDTO) {
        return orderDb.save(orderDTO);
    }
    
    @Override
    public List<Order> retrieveAllOrders() {
        return orderDb.findAll();
    }

    @Override
    public List<Order> getOrdersByCustomerId(UUID id) {
        return orderDb.findByCustomerId(id);
    }

    @Override
    public List<Order> getOrdersBySellerId(UUID id) {
        return orderDb.findBySellerId(id);
    }

    @Override
    public Order getRestOrderById(UUID id) {
        for (Order order : retrieveAllOrders()) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    };

    @Override
    public Order updateRestOrderStatus(Order orderFromDTO) {
        Order order = getRestOrderById(orderFromDTO.getId());
        if (order != null){
            if (order.getStatus() >= 0 || order.getStatus() < 5) {
                order.setStatus(orderFromDTO.getStatus() + 1);
    
                Date waktuSaatIni = new Date();
                order.setUpdatedAt(waktuSaatIni);
    
                orderDb.save(order);
                
                return order;
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid order status"
                );
            }
        }
        throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Order not found"
        );
    }

    private List<OrderItemDTO> convertOrderItemsToDTOList(List<OrderItem> orderItems) {
        List<OrderItemDTO> orderItemsDTO = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setId(orderItem.getOrderId());
            orderItemDTO.setProductId(orderItem.getProductId());
            orderItemDTO.setQuantity(orderItem.getQuantity());
            orderItemDTO.setProductName(orderItem.getProductName());
            orderItemDTO.setProductPrice(orderItem.getProductPrice());
    
            orderItemsDTO.add(orderItemDTO);
        }
        return orderItemsDTO;
    }

    @Override
    public List<OrderDTO> getAllOrderDTO(List<Order> listOrder) {
        List<OrderDTO> listOrderDTO = new ArrayList<>();
        for (Order order : listOrder) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setCustomerId(order.getCustomerId());
            orderDTO.setSellerId(order.getSellerId());
            orderDTO.setId(order.getId());
            orderDTO.setCreatedAt(order.getCreatedAt());
            orderDTO.setUpdatedAt(order.getUpdatedAt());
            orderDTO.setStatus(order.getStatus());
            orderDTO.setTotalPrice(order.getTotalPrice());

            List<OrderItemDTO> orderItemsDTO = convertOrderItemsToDTOList(order.getListOrderItem());
            orderDTO.setOrderItems(orderItemsDTO);

            listOrderDTO.add(orderDTO);
        }
        return listOrderDTO;
    }

    private final WebClient webClient;
    public OrderRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082")
                            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .build();
    }

    @Override
    public CatalogDTO viewAllCatalogByCatalogId(OrderItem productId) {
                CatalogDTO listCatalog = webClient
                .get()
                .uri("/api/catalog/{productId}")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CatalogDTO>() {})
                .block();

        return listCatalog;
    }
    
    
}
