package com.apapedia.order.dto.request;

import com.apapedia.order.model.Cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateCartItemRequestDTO {
    private UUID productId;

    private Cart cart;

    private UUID cartId;


    @NotNull(message = "Kuantitas tidak boleh kosong")
    private Integer quantity;
}
