package com.apapedia.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.apapedia.order.model.Order;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOrderItemRequestDTO {
    private Order order;
    private int quantity;
    private String productName;
    private int productPrice;
}
