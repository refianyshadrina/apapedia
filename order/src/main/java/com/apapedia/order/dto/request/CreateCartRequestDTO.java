package com.apapedia.order.dto.request;

import org.hibernate.validator.constraints.UUID;

import com.apapedia.order.model.CartItem;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateCartRequestDTO {
    private UUID userId;

    @NotNull(message = "Harga tidak boleh kosong")
    private Integer totalPrice;

    private List<CartItem> listCartItem;
}
