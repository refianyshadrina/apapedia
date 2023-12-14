package com.apapedia.order.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {

    private UUID categoryId;
    private String CategoryName;
    private List<CatalogDTO> listCatalog;
}
