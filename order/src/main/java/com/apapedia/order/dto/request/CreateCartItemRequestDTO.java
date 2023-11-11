package com.apapedia.order.dto.request;

import org.hibernate.validator.constraints.UUID;

import com.apapedia.order.model.Cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateCartItemRequestDTO {
    private UUID productId;

    private Cart cartId;

    @NotNull(message = "Kuantitas tidak boleh kosong")
    private Integer quantity;
}
