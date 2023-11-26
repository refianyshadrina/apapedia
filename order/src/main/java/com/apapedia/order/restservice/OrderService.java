package com.apapedia.order.restservice;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.model.Order;
import com.apapedia.order.model.UserDummy;
import com.apapedia.order.model.SellerDummy;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    
    void createOrders(Order order);

    public List<Order> retrieveAllOrders();

    public Order getOrdersByUserId(UserDummy id);

    public Order getOrdersBySellerId(SellerDummy id);

    Order getRestOrderById(UUID id);

    Order updateRestOrderStatus(Order orderFromDTO);

    void adjustBalances(Order order);
}

