package com.apapedia.order.restservice;

import com.apapedia.order.model.CartItem;
import com.apapedia.order.dto.CatalogDTO;
import com.apapedia.order.dto.OrderDTO;
import com.apapedia.order.model.Order;
import com.apapedia.order.model.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderRestService {
    
    Order createOrders(Order orderDTO);

    public List<Order> retrieveAllOrders();

    public List<Order> getOrdersByCustomerId(UUID id);

    public List<Order> getOrdersBySellerId(UUID id);

    Order getRestOrderById(UUID id);

    Order updateRestOrderStatus(Order orderFromDTO);

    List<OrderDTO> getAllOrderDTO(List<Order> listOrder);

    public CatalogDTO viewAllCatalogByCatalogId(OrderItem productId);
    


}

