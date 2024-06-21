package com.example.demo.mapper;

import com.example.demo.dto.BookDTO;
import com.example.demo.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "genre", target = "genre")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "isbn", target = "isbn")
    @Mapping(source = "publishDate", target = "publishDate")
    @Mapping(source = "copiesAvailable", target = "copiesAvailable")
    Book toModel(BookDTO bookDTO);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "genre", target = "genre")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "isbn", target = "isbn")
    @Mapping(source = "publishDate", target = "publishDate")
    @Mapping(source = "copiesAvailable", target = "copiesAvailable")
    BookDTO toDto(Book book);
}
