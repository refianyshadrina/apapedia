package com.apapedia.frontend.payloads;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CatalogDTO {

    private UUID id;
    private UUID sellerId;
    private int price;
    private String productName;
    private String productDescription;
    private CategoryDTO category;
    private int stock;
    private byte[] image;
    private boolean isDeleted;

}