package com.apapedia.order.dto.request;

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
    private Integer quantity;
}
