package com.apapedia.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seller_dummy")
public class SellerDummy {
    @Id
    @Column(name = "seller_id", nullable = false)

    private UUID sellerId = UUID.randomUUID();

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "balance", nullable = false)
    private Long balance;

    @OneToMany(mappedBy = "sellerId", cascade = CascadeType.ALL)
    private List<Order> listOrder;
}

