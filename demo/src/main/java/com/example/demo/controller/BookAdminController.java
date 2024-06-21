package com.example.demo.controller;

import com.example.demo.aspect.LoggableController;
import com.example.demo.dto.BookDTO;
import com.example.demo.mapper.BookMapper;
import com.example.demo.services.inter.BookService;
import com.example.demo.validation.BookCreate;
import com.example.demo.validation.BookUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 27.05.2024
 */
@Tag(name = "Create/Update/Delete Books", description = "CRUD operations with Books")
@RestController
@LoggableController
@RequestMapping("/api/books/admin")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookAdminController {
    BookService bookService;
    String isbnMessage = "Such isbn already exists";
    String titleMessage = "Such title already exists";
    String teapotMessage = "Be more careful!";

    @Validated({BookCreate.class})
    @Operation(summary = "Create a Book", description = "Allow user to create a book in DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created book"),
            @ApiResponse(responseCode = "400", description = "Mistakes in request body"),
            @ApiResponse(responseCode = "403", description = "User not allowed to do such operation"),
            @ApiResponse(responseCode = "409", description = "Book with such data already exist")
    })
    @PostMapping("/create")
    public ResponseEntity<?> create(@Parameter(description = "Details of the book to be created")
                                        @RequestBody @Valid BookDTO bookDTO) throws ExecutionException, InterruptedException {
        var bookByIsbn = bookService.getBookByIsbn(bookDTO.getIsbn());
        var bookByTitle = bookService.getBookByTitle(bookDTO.getTitle());

        if (bookByIsbn.get().isEmpty()) {
            if (bookByTitle.get().isEmpty()) {
                boolean res = bookService.save(bookDTO).get();
                if (res) return ResponseEntity.status(201).body(bookService.getBookByIsbn(bookDTO.getIsbn()).get());
                else return ResponseEntity.status(418).body(teapotMessage);
            } else return ResponseEntity.status(409).body(titleMessage);
        } else return ResponseEntity.status(409).body(isbnMessage);
    }

    @Validated({BookUpdate.class})
    @Operation(summary = "Update a Book", description = "Allow user to update a book in DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated book"),
            @ApiResponse(responseCode = "400", description = "Mistakes in request body"),
            @ApiResponse(responseCode = "403", description = "User not allowed to do such operation"),
            @ApiResponse(responseCode = "409", description = "Book with such data already exist")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Parameter(description = "Details of the book to be updated")
                                        @PathVariable Integer id, @RequestBody @Valid BookDTO bookDTO) throws ExecutionException, InterruptedException {
        bookDTO.setId(id);
        var bookByIsbn = bookService.getBookByIsbn(bookDTO.getIsbn()).get();
        var bookByTitle = bookService.getBookByTitle(bookDTO.getTitle()).get();

        if ((bookByIsbn.isPresent() && !bookByIsbn.get().getId().equals(id)) || bookByIsbn.isEmpty()) {
            if ((bookByTitle.isPresent() && !bookByTitle.get().getId().equals(id)) || bookByTitle.isEmpty()) {
                boolean res = bookService.update(bookDTO).get();
                if (res) return ResponseEntity.status(201).body(BookMapper.INSTANCE.toModel(bookDTO));
                else return ResponseEntity.status(418).body(teapotMessage);
            } else return ResponseEntity.status(409).body(titleMessage);
        } else return ResponseEntity.status(409).body(isbnMessage);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a Book", description = "Allow user to delete a book from DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted book"),
            @ApiResponse(responseCode = "403", description = "User not allowed to do such operation"),
    })
    public ResponseEntity<?> delete(@Parameter(description = "ID of the book to be deleted")
                                        @PathVariable Integer id) throws ExecutionException, InterruptedException {
        boolean res = bookService.delete(id).get();
        if (res) return ResponseEntity.status(200).body("Book " + id + " deleted successfully!");
        else return ResponseEntity.status(418).body(teapotMessage);
    }
}
