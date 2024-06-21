package com.example.demo.dto;

import com.example.demo.validation.BookCreate;
import com.example.demo.validation.BookNameUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Data transfer object representing a book")
public class BookDTO {
    @Schema(description = "Unique identifier of the book. Will be calculated in DB. Not necessary to be sent", example = "1")
    Integer id;

    @NotEmpty(message = "Book title can't be empty!")
    @BookNameUnique(groups = BookCreate.class)
    @Schema(description = "Title of the book", example = "The Great Gatsby")
    String title;

    @NotEmpty(message = "A book must have an author!")
    @Schema(description = "Author of the book", example = "F. Scott Fitzgerald")
    String author;

    @Schema(description = "Genre of the book", example = "Novel")
    String genre;
    @Schema(description = "Description of the book", example = "A novel set in the Roaring Twenties")
    String description;

    @NotEmpty(message = "A isbn can't be empty!")
    @NotNull(message = "Where is 'isbn', Lebovsky?!")
    @Schema(description = "ISBN of the book", example = "978-3-16-148410-0")
    String isbn;

    @Schema(format = "date", description = "Publish date of the book", example = "1925-04-10")
    Date publishDate;

    @DecimalMin(value = "0", message = "The number of books cannot be a negative number")
    @NotNull(message = "A book must have a quantity of available copies!")
    @Schema(description = "Number of available copies of the book.", example = "3")
    Integer copiesAvailable;
}
