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
@Table(name = "user_dummy")
public class UserDummy {
    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId = UUID.randomUUID();

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Order> listOrder;

}
