package com.apapedia.order.dto;

import org.mapstruct.Mapper;

import com.apapedia.order.dto.request.CreateUserDummyRequestDTO;
import com.apapedia.order.model.UserDummy;

@Mapper(componentModel = "spring")
public interface UserDummyMapper {
    UserDummy createUserDummyRequestDTOToUserDummy(CreateUserDummyRequestDTO createUserDummyRequestDTO);
}
