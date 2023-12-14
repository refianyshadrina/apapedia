package com.apapedia.frontend.payloads;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private UUID id;
    private Date createdAt;
    private Date updatedAt;
    private Integer status;
    private Integer totalPrice;
    private UUID customerId;
    private UUID sellerId;
    private List<OrderItemDTO> orderItems;
}
