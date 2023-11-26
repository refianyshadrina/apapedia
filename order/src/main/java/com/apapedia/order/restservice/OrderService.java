package com.apapedia.order.restservice;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.model.Order;
import com.apapedia.order.model.UserDummy;
import com.apapedia.order.model.SellerDummy;

import java.util.List;

public interface OrderService {
    
    void createOrder(Order order);

    public List<Order> retrieveAllOrder();

    public Order getOrderByUserId(UserDummy id);

    public Order getOrderBySellerId(SellerDummy id);

}

