package com.example.demo.mapper;

import com.example.demo.dto.BusyBookDTO;
import com.example.demo.model.BusyBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 31.05.2024
 */
@Mapper
public interface BusyBookMapper {
    BusyBookMapper INSTANCE = Mappers.getMapper(BusyBookMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "book", target = "book")
    @Mapping(source = "dateStart", target = "dateStart")
    @Mapping(source = "dateEnd", target = "dateEnd")
    BusyBook toModel(BusyBookDTO bookDTO);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "book", target = "book")
    @Mapping(source = "dateStart", target = "dateStart")
    @Mapping(source = "dateEnd", target = "dateEnd")
    BusyBookDTO toDto(BusyBook book);
}
