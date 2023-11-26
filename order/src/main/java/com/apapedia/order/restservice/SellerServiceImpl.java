package com.apapedia.order.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.order.model.SellerDummy;
import com.apapedia.order.repository.SellerDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerDb sellerDb;

    @Override
    public SellerDummy createSellerDummy(SellerDummy seller) {
        return sellerDb.save(seller);
    }
    
}
