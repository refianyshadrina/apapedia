package com.apapedia.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {

    // punya alia
    @Id
    @Column(name = "cart_item_id")
    private UUID id = UUID.randomUUID();

    @NotNull
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    // punya alia
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    @NotNull
    @Column(name = "cart_id", nullable = false)
    private UUID cartId;

    // cart_id jingga
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart", referencedColumnName = "cart_id")
    private Cart cart;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
