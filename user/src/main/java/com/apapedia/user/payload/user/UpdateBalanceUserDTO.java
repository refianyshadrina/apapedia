package com.apapedia.user.payload.user;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateBalanceUserDTO {
    private UUID id;

    private long balance;
}

