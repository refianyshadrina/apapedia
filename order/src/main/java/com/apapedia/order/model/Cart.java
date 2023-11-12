package com.apapedia.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
@JsonIgnoreProperties(value = {"listCartItem"}, allowSetters = true)

public class Cart {
    // punya jingga
    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    // punya alia
    @Id
    @Column(name = "cart_id")
    private UUID cartId = UUID.randomUUID();

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserDummy user;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Integer totalPrice = 0;

    // listCartItem jingga
    @JsonIgnore
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CartItem> listCartItem;
}
