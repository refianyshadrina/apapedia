package com.apapedia.catalog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "catalog")
// @JsonIgnoreProperties(value={"category"}, allowSetters = true)
public class Catalog {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(name = "seller_id", nullable = true)
    private UUID sellerId = UUID.randomUUID();

    @NotNull
    @Column(name = "price", nullable = false)
    private int price;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(name = "product_description", nullable = false)
    private String productDescription;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;

    @NotNull
    @Column(name = "stock", nullable = false)
    private int stock;

    @Lob
    @Column(name = "image", nullable = true)
    private byte[] image;

}