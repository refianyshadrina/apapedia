package com.apapedia.order.dto;

import org.mapstruct.Mapper;

import com.apapedia.order.dto.request.CreateOrdersRequestDTO;
import com.apapedia.order.dto.request.UpdateOrderRequestDTO;
import com.apapedia.order.model.Order;


@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order CreateOrdersRequestDTOToOrders(CreateOrdersRequestDTO orderDTO);

    Order updateOrderRequestDTOToOrder(UpdateOrderRequestDTO updateOrderRequestDTO);
}
