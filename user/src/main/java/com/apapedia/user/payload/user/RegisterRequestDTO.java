package com.apapedia.user.payload.user;


import java.util.UUID;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String nama;
    private String email;
    private String username;
    private String password;
    private Long category;
    private Long balance;
    private String address;
    private UUID cartId;
}
