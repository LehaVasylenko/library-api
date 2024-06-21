package com.example.demo.mapper;

import com.example.demo.dto.user.UserDTO;
import com.example.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "pass")
    @Mapping(source = "passEnc", target = "passEnc")
    @Mapping(source = "role", target = "role")
    User toModel(UserDTO userDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "pass", target = "password")
    @Mapping(source = "passEnc", target = "passEnc")
    @Mapping(source = "role", target = "role")
    UserDTO toDto(User user);
}
