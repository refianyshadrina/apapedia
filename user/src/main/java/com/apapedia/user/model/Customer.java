package com.apapedia.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer extends UserModel {

    // ke cart
    @NotNull
    @Column(name = "cart_id", nullable = false)
    private UUID cartId;
}

