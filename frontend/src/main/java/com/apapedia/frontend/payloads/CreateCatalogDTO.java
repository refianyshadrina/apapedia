package com.apapedia.frontend.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCatalogDTO {
    private UUID sellerId;
    private int price;
    private String productName;
    private String productDescription;
    private String category;
    private int stock;
    private byte[] image;
    private boolean isDeleted;
}
