package com.apapedia.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
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
