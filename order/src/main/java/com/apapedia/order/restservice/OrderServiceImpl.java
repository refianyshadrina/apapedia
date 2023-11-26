package com.apapedia.order.restservice;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.Order;
import com.apapedia.order.model.UserDummy;
import com.apapedia.order.model.SellerDummy;
import com.apapedia.order.repository.CartDb;
import com.apapedia.order.repository.OrderDb;
import com.apapedia.order.repository.UserDb;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.xml.catalog.Catalog;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
   
    @Autowired
    UserDb userDb;

    @Autowired
    OrderDb orderDb;

    @Override
    public void createOrder(Order order) {
        orderDb.save(order);
    };

    @Override
    public List<Order> retrieveAllOrder() {
        return orderDb.findAll();
    }

    @Override
    public Order getOrderByUserId(UserDummy id) {
        for (Order order : retrieveAllOrder()) {
            if (order.getUserId().equals(id)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public Order getOrderBySellerId(SellerDummy id) {
        for (Order order : retrieveAllOrder()) {
            if (order.getSellerId().equals(id)) {
                return order;
            }
        }
        return null;
    }

}