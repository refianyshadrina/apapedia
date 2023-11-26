package com.apapedia.catalog.restcontroller;

import com.apapedia.catalog.model.Catalog;
import com.apapedia.catalog.model.Category;
import com.apapedia.catalog.restservice.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
public class CategoryRestController {
    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/category")
    public List<Category> retrieveAllCategory() {
        return categoryService.retrieveAllCategory();
    }
}
