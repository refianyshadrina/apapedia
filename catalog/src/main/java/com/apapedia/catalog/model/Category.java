package com.apapedia.catalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    private UUID categoryId = UUID.randomUUID();

    @NotNull
    @Column(name = "category_name", nullable = false)
    private String CategoryName;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Catalog> listCatalog = new ArrayList<>();

}