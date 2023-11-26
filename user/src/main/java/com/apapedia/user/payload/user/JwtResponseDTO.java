package com.apapedia.user.payload.user;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class JwtResponseDTO {
    private String token;
    private UUID uuid;
    private String username;
    private String email;
    private List<String> roles;
    //expirationdate
    //claims?

    public JwtResponseDTO(String token, UUID uuid, String username, String email, List<String> roles) {
        this.token = token;
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public JwtResponseDTO() {
        
    }
}

