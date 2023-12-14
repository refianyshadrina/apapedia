package com.apapedia.frontend.restService;

import com.apapedia.frontend.payloads.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryRestService {

    List<CategoryDTO> retrieveAllCategories();

    CategoryDTO getCategoryById(UUID categoryId);

}
