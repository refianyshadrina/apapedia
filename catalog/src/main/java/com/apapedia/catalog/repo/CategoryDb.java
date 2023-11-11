package com.apapedia.catalog.repo;

import com.apapedia.catalog.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CategoryDb extends JpaRepository<Category, Long> {

}