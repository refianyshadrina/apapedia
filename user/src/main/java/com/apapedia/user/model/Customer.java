package com.apapedia.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
@SQLDelete(sql = "UPDATE customer SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
public class Customer extends UserModel {

    // ke cart
    @NotNull
    @Column(name = "cart_id", nullable = false)
    private UUID cartId;
}

