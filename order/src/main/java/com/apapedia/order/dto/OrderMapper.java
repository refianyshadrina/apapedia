package com.apapedia.order.dto;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.apapedia.order.dto.request.CreateOrderItemRequestDTO;
import com.apapedia.order.dto.request.CreateOrdersRequestDTO;
import com.apapedia.order.dto.request.UpdateOrderRequestDTO;
import com.apapedia.order.model.Order;
import com.apapedia.order.model.OrderItem;


@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order CreateOrdersRequestDTOToOrder(CreateOrdersRequestDTO orderDTO);


    OrderItem CreateOrderItemRequestDTOToOrderItem(CreateOrderItemRequestDTO orderItemDTO);

    Order updateOrderRequestDTOToOrder(UpdateOrderRequestDTO updateOrderRequestDTO);
    
    @AfterMapping
    default void assignOrderItem(@MappingTarget Order order, CreateOrdersRequestDTO createOrderRequestDTO) {
        List<OrderItem> OrderItemList = createOrderRequestDTO.getListOrderItem();
        if (OrderItemList != null && !OrderItemList.isEmpty()) {
            for (OrderItem orderItem : OrderItemList) {
                orderItem.setOrders(order);
            }
        }
    }
}
