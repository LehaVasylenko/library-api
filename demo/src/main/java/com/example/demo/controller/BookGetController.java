package com.example.demo.controller;

import com.example.demo.aspect.LoggableController;
import com.example.demo.dto.BookDTO;
import com.example.demo.services.inter.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Tag(name = "Get Books", description = "Get operations with Books")
@RestController
@LoggableController
@RequestMapping("/api/books/get")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookGetController {
    BookService bookService;

    @Operation(summary = "Get a list of all Books", description = "Returns a list of book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved books"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getBooks() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(bookService.getBooks().get());
    }


    @Operation(summary = "Get a book by its ID", description = "Returns a single book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved books"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Wrong id")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getBooksById(@Parameter(description = "ID of the book to be retrieved")
                                                              @PathVariable Integer id) throws ExecutionException, InterruptedException {
        var book = bookService.getBook(id);
        if (book.get().isEmpty()) return ResponseEntity.status(404).body("No book with id " + id);
        else return ResponseEntity.ok(book.get());
    }

    @Operation(summary = "Get a book by its ISBN", description = "Returns a single book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved books"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Wrong isbn")
    })
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<?> getBooksByIsbn(@Parameter(description = "ISBN of the book to be retrieved")
                                                                @PathVariable String isbn) throws ExecutionException, InterruptedException {
        var book = bookService.getBookByIsbn(isbn);
        if (book.get().isEmpty()) return ResponseEntity.status(404).body("No book with isbn " + isbn);
        else return ResponseEntity.ok(book.get());
    }

    @Operation(summary = "Get a book by its Title", description = "Returns a single book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved books"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Wrong title")
    })
    @GetMapping("/title/{title}")
    public ResponseEntity<?> getBooksByTitle(@Parameter(description = "Title of the book to be retrieved")
                                                                 @PathVariable String title) throws ExecutionException, InterruptedException {
        var book = bookService.getBookByTitle(title);
        if (book.get().isEmpty()) return ResponseEntity.status(404).body("Np book with title " + title);
        else return ResponseEntity.ok(book.get());
    }

    @Operation(summary = "Get a books by their Author", description = "Returns a List of books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved books"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "No such author")
    })
    @GetMapping("/author/{author}")
    public ResponseEntity<?> getBooksByAuthor(@Parameter(description = "Author of the books to be retrieved")
                                                              @PathVariable String author) throws ExecutionException, InterruptedException {
        var book = bookService.getBookByAuthor(author).get();
        if (book.isEmpty()) return ResponseEntity.status(404).body("It seems like no such Author as " + author);
        else return ResponseEntity.ok(book);
    }

    @Operation(summary = "Get a books by its Genre", description = "Returns a List of books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved books"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "No such genre")
    })
    @GetMapping("/genre/{genre}")
    public ResponseEntity<?> getBooksById(@Parameter(description = "Genre of the books to be retrieved")
                                                          @PathVariable String genre) throws ExecutionException, InterruptedException {
        var book = bookService.getBookByGenre(genre);
        if (book.get().isEmpty()) return ResponseEntity.status(404).body("It seems like no one book in " + genre + " genre");
        else return ResponseEntity.ok(book.get());
    }
}
