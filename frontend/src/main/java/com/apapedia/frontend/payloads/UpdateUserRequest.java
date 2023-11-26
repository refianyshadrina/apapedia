package com.apapedia.frontend.payloads;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRequest {
    private UUID id;

    private String username;

    private String nama;

    private String address;

    private String email;

    private String password;
}
