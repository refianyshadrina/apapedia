package com.apapedia.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.Date;
import java.util.List;

import com.apapedia.order.model.OrderItem;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateOrdersRequestDTO {
    private Date createdAt = new Date();
    private Date updatedAt = this.createdAt;
    private Integer status = 0;
    private Integer totalPrice;
    private UUID customerId;
    private UUID sellerId;
    private List<OrderItem> listOrderItem;
}
