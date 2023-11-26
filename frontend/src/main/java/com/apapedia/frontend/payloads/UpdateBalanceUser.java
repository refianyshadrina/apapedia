package com.apapedia.frontend.payloads;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateBalanceUser {
    private UUID id;

    private long balance;
}
