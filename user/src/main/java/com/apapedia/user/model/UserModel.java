package com.apapedia.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class UserModel implements Serializable {
    @Id
    private UUID id = UUID.randomUUID();



    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull
    @Size(max = 50)
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Lob
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "balance", nullable = false)
    private Long balance;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name="created_at", nullable = false)
    @DateTimeFormat(pattern = "dd-mm-yyy, hh:mm")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name="updated_at", nullable = false)
    @DateTimeFormat(pattern = "dd-mm-yyy, hh:mm")
    private LocalDateTime updatedAt;

}
