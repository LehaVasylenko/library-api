package com.example.demo.controller;

import com.example.demo.aspect.LoggableController;
import com.example.demo.dto.auth.AuthResponse;
import com.example.demo.dto.user.LoginDTO;
import com.example.demo.dto.user.RemindPasswordDTO;
import com.example.demo.services.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@Tag(name = "Login", description = "User can login to have possibility to do CRUD operations")
@RestController
@LoggableController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {
    LoginService loginService;

    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully login"),
            @ApiResponse(responseCode = "400", description = "Mistakes in request body"),
            @ApiResponse(responseCode = "403", description = "User not allowed to do such operation"),
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDTO request) throws ExecutionException, InterruptedException {
        CompletableFuture<AuthResponse> result = loginService.login(request);
        return ResponseEntity.ok(result.get());
    }

    @Operation(summary = "Remind a password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "400", description = "Mistakes in request body"),
            @ApiResponse(responseCode = "403", description = "User not allowed to do such operation"),
    })
    @PostMapping("/remind")
    public ResponseEntity<?> remind(@Valid @RequestBody RemindPasswordDTO request) throws ExecutionException, InterruptedException {
        CompletableFuture<?> result = loginService.remindPassword(request);
        return ResponseEntity.ok(result.get());
    }
}
