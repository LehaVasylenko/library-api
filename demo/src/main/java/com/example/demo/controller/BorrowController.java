package com.example.demo.controller;

import com.example.demo.aspect.LoggableController;
import com.example.demo.dto.BorrowRequestDTO;
import com.example.demo.services.BorrowService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 29.05.2024
 */
@Tag(name = "Borrow/Return Books", description = "Borrow and Return operations with Books")
@RestController
@LoggableController
@RequestMapping("/api/borrow")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BorrowController {
    BorrowService borrowService;

    @Operation(summary = "Borrow book/books", description = "User able to borrow a book or List of Books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully borrowed books"),
            @ApiResponse(responseCode = "400", description = "Mistakes in request body"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
    })
    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBooks(@AuthenticationPrincipal UserDetails userDetails,
                                         @Parameter(description = "ISBN of the book and date till user want to read a book")
                                         @Valid @RequestBody List<BorrowRequestDTO> borrowRequests) throws ExecutionException, InterruptedException {
        String username = userDetails.getUsername();
        boolean success = borrowService.borrowBooks(username, borrowRequests).get();
        if (success) {
            return ResponseEntity.ok("Books borrowed successfully!");
        } else {
            return ResponseEntity.status(418).body("Failed to borrow books!");
        }
    }

    @PostMapping("/return")
    @Operation(summary = "Return book/books", description = "User able to return a book or List of Books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned books"),
            @ApiResponse(responseCode = "400", description = "Mistakes in request body"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "User have no books to be returned"),
    })
    public ResponseEntity<?> returnBooks(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<String> isbns) throws ExecutionException, InterruptedException {
        String username = userDetails.getUsername();
        boolean success = borrowService.returnBooks(username, isbns).get();
        if (success) {
            return ResponseEntity.ok("Books returned successfully!");
        } else {
            return ResponseEntity.status(404).body("Failed to return books!");
        }
    }
}

