package com.apapedia.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class OrderItemDTO {
    private UUID id;
    private UUID productId;
    private Integer quantity;
    private String productName;
    private Integer productPrice;
}
