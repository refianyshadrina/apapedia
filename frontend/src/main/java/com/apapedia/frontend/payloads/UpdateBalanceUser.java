package com.apapedia.frontend.payloads;

import java.util.UUID;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateBalanceUser {
    private UUID id;

    @PositiveOrZero(message = "Please input a valid number")
    private long balance;
}
