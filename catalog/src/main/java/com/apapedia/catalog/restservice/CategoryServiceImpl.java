package com.apapedia.catalog.restservice;

import com.apapedia.catalog.model.Catalog;
import com.apapedia.catalog.model.Category;
import com.apapedia.catalog.repository.CategoryDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDb categoryDb;

    @Override
    public List<Category> retrieveAllCategory() {
        return categoryDb.findAll();
    }
}