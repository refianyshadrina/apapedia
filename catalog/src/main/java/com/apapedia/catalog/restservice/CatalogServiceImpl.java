package com.apapedia.catalog.restservice;

import com.apapedia.catalog.repo.CatalogDb;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private CatalogDb catalogDb;
}
