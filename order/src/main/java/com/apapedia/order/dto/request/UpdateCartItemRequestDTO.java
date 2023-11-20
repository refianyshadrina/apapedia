package com.apapedia.order.dto.request;

import com.apapedia.order.model.Cart;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCartItemRequestDTO {
    @NotNull(message = "ID cannot be null")
    private UUID id;

    private UUID productId;

    private Cart cart;

    @NotNull(message = "Field is required")
    private Integer quantity;
}
