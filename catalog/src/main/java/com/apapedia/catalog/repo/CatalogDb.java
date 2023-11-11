package com.apapedia.catalog.repo;

import com.apapedia.catalog.model.Catalog;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface CatalogDb extends JpaRepository<Catalog, UUID> {
    List<Catalog> findAll();

}