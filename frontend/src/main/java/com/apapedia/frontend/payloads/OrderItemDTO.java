package com.apapedia.frontend.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDTO {
    private UUID orderId;
    private UUID productId;
    private Integer quantity;
    private String productName;
    private Integer productPrice;
    private OrderDTO orders;
}
