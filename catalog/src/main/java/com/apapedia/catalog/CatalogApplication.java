package com.apapedia.catalog;

import jakarta.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.apapedia.catalog.model.Category;
import com.apapedia.catalog.repository.CategoryDb;
import com.apapedia.catalog.restservice.CategoryService;

@SpringBootApplication
public class CatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(CategoryService categoryService,  CategoryDb categoryDb) {
		
			return args -> {
				
				Category category = new Category();
				category.setCategoryName("Elektronik");

				categoryDb.save(category);

			};

		
	}

}
