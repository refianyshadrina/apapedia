package com.apapedia.user.payload;

import java.util.UUID;
import lombok.Data;

@Data
public class CartDTO {
    private UUID cartId;
    private Integer totalPrice;
}
