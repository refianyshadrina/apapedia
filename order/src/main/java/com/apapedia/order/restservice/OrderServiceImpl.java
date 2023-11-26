package com.apapedia.order.restservice;

import com.apapedia.order.model.Cart;
import com.apapedia.order.model.CartItem;
import com.apapedia.order.model.Order;
import com.apapedia.order.model.UserDummy;
import com.apapedia.order.model.SellerDummy;
import com.apapedia.order.repository.CartDb;
import com.apapedia.order.repository.OrderDb;
import com.apapedia.order.repository.SellerDb;
import com.apapedia.order.repository.UserDb;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.catalog.Catalog;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    SellerDb sellerDb;
   
    @Autowired
    UserDb userDb;

    @Autowired
    OrderDb orderDb;

    @Override
    public void createOrders(Order order) {
        orderDb.save(order);
    };

    @Override
    public List<Order> retrieveAllOrders() {
        return orderDb.findAll();
    }

    @Override
    public Order getOrdersByUserId(UserDummy id) {
        for (Order order : retrieveAllOrders()) {
            if (order.getUserId().equals(id)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public Order getOrdersBySellerId(SellerDummy id) {
        for (Order order : retrieveAllOrders()) {
            if (order.getSellerId().equals(id)) {
                return order;
            }
        }
        return null;
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
            if (orderFromDTO.getStatus() - order.getStatus() == 1) {
                order.setStatus(orderFromDTO.getStatus());
    
                Date waktuSaatIni = new Date();
                order.setUpdatedAt(waktuSaatIni);
    
                orderDb.save(order);

                adjustBalances(order);
                
                return order;
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid order status update sequence"
                );
            }
        }
        throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Order not found"
        );
    }

    @Override
    public void adjustBalances(Order order) {
        if (order.getStatus() == 5) {
            SellerDummy seller = order.getSellerId();
            UserDummy customer = order.getUserId();

            Integer totalPrice = order.getTotalPrice();

            seller.setBalance(seller.getBalance() + totalPrice);

            customer.setBalance(customer.getBalance() - totalPrice);

            sellerDb.save(seller);
            userDb.save(customer);
        }
    }
}