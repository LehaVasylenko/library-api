package com.example.demo.controller;

import com.example.demo.aspect.LoggableController;
import com.example.demo.dto.user.RegisterDTO;
import com.example.demo.services.AuthUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Tag(name = "Registration")
@Slf4j
@RestController
@LoggableController
@RequestMapping("/api/register")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterController {
    AuthUserService authUserService;

    String userRegisterOK = "User registered successfully\n";
    String adminRegisterOK = "Admin registered successfully\n";
    String email = "Check your email to get a credentials for login\n";

    String registerNotOK = "Such email already registered!";

    @Operation(summary = "Register as User", description = "In this case you won't be able to Create/Update/Delete books. Only Read/Borrow/Return")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created User. Check your email for credentials for login"),
            @ApiResponse(responseCode = "400", description = "Mistakes in request body"),
            @ApiResponse(responseCode = "409", description = "Such User with email and role already exists"),
    })
    @PostMapping("/user")
    public ResponseEntity<String> registerUser(@Parameter(description = "Registration form")
            @Valid @RequestBody RegisterDTO request) throws ExecutionException, InterruptedException {
        CompletableFuture<Boolean> result = authUserService.registerUser(request);
        if (result.get())
            return ResponseEntity.status(HttpStatus.CREATED).body(userRegisterOK + email);
        else
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body(registerNotOK);
    }

    @Operation(summary = "Register as Administrator", description = "Full access mode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created Admin. Check your email for credentials for login"),
            @ApiResponse(responseCode = "400", description = "Mistakes in request body"),
            @ApiResponse(responseCode = "409", description = "Such User with email and role already exists"),
    })
    @PostMapping("/admin")
    public ResponseEntity<String> registerAdmin(@Parameter(description = "Registration form")
            @Valid @RequestBody RegisterDTO request) throws ExecutionException, InterruptedException {
        CompletableFuture<Boolean> result = authUserService.registerAdmin(request);
        if (result.get())
            return ResponseEntity.status(HttpStatus.CREATED).body(adminRegisterOK + email);
        else
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body(registerNotOK);
    }
}
