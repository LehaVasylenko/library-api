package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 29.05.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Data transfer object to borrow a book")
public class BorrowRequestDTO {
    @Schema(description = "ISBN of book need to borrow", example = "978-3-16-148410-0")
    @NotNull(message = "Where is ISBN, Lebovsky?!")
    String isbn;
    @Schema(format = "date", description = "Date till book will be borrowed", example = "2024-04-10")
    LocalDate returnDate;

}
