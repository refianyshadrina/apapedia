package com.apapedia.user.payload;


import java.util.UUID;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nama;
    private String email;
    private String username;
    private String password;
    private String category;
    private Long balance;
    private String address;
    private UUID cartId;
}
