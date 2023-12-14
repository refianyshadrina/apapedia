package com.apapedia.catalog.restservice;

import com.apapedia.catalog.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    List<Category> retrieveAllCategory();
    Optional<Category> retrieveByCategoryId(UUID id);


}
