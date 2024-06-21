package com.example.demo.mapper;

import com.example.demo.dto.bugreport.BugReportDTO;
import com.example.demo.model.BugReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 31.05.2024
 */
@Mapper
public interface BugReportMapper {
    BugReportMapper INSTANCE = Mappers.getMapper(BugReportMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "subject", target = "subject")
    @Mapping(source = "reportLink", target = "reportLink")
    BugReport toModel(BugReportDTO bugReportDTO);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "subject", target = "subject")
    @Mapping(source = "reportLink", target = "reportLink")
    BugReportDTO toDto(BugReport bugReport);
}
