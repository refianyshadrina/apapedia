package com.apapedia.user.payload;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SellerRegisterRequest {
    private String nama;
    private String email;
    private String username;
    private String password;
    private String category;
    private Long balance;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
