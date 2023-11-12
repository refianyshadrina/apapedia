package com.apapedia.catalog.repository;

import com.apapedia.catalog.model.Catalog;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CatalogDb extends JpaRepository<Catalog, UUID> {
    List<Catalog> findAll();
    
    @Query("SELECT c FROM Catalog c ORDER BY LOWER(c.productName) ASC")
    List<Catalog> findAllOrderByProductNameIgnoreCaseAsc();

    Optional<Catalog> findById(UUID id);

    List<Catalog> findBySellerId(long sellerId);

}