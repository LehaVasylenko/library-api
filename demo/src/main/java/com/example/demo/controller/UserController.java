package com.example.demo.controller;

import com.example.demo.aspect.LoggableController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 29.05.2024
 */
@Tag(name = "Give info about user")
@RestController
@LoggableController
@RequestMapping("/api")
public class UserController {

    @Operation(summary = "Information about User", description = "Return Username and Role about user who make a request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
    })
    @GetMapping("/userinfo")
    public ResponseEntity<String> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().toString();
        return ResponseEntity.ok("User: " + username + ", Role: " + role);
    }
}

