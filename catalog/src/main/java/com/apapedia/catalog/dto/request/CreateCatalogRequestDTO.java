package com.apapedia.catalog.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.Valid;
import com.apapedia.catalog.model.Catalog;
import com.apapedia.catalog.model.Category;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCatalogRequestDTO {
    private UUID sellerId;
    private int price;
    private String productName;
    private String productDescription;

    @NotNull(message = " is still empty")
    private UUID category;

    private int stock;

    @NotNull(message = "penerbit is still empty")
    private String image;

    

    
}
