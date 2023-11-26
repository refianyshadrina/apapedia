package com.apapedia.order.dto;

import org.mapstruct.Mapper;

import com.apapedia.order.dto.request.CreateSellerDummyRequestDTO;
import com.apapedia.order.model.SellerDummy;

@Mapper(componentModel = "spring")
public interface SellerDummyMapper {
    SellerDummy createSellerDummyRequestDTOToSellerDummy(CreateSellerDummyRequestDTO createSellerDummyRequestDTO);
}
