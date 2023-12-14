package com.apapedia.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import com.apapedia.order.dto.CatalogDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @Column(name = "cart_item_id")
    private UUID id = UUID.randomUUID();

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart", referencedColumnName = "cart_id")
    private Cart cart;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // public void fromCatalogDetail(CatalogDTO catalog) {
    //     productId = catalog.getId();
    //     quantity = catalog.getPrice();
    // }

    
}
