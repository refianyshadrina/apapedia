package com.apapedia.user.payload;


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
}
