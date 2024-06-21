package com.example.demo.controller;

import com.example.demo.aspect.LoggableController;
import com.example.demo.dto.bugreport.BugReportRequest;
import com.example.demo.services.BugReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 31.05.2024
 */
@Tag(name = "Send Bug Report", description = "User can send a bug report, if he find a bug. Bug report should be in Google Docs")
@RestController
@LoggableController
@RequestMapping("/api/bug")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BugReportController {
    BugReportService bugReportService;

    @Operation(summary = "Create a Report", description = "Allow user to create a bug report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created report/reports"),
            @ApiResponse(responseCode = "400", description = "Mistakes in request body"),
            @ApiResponse(responseCode = "403", description = "User not allowed to do such operation"),
    })
    @PostMapping("/report")
    public ResponseEntity<?> borrowBooks(@AuthenticationPrincipal UserDetails userDetails,
                                         @Parameter(description = "Subject and link to report")
                                         @Valid @RequestBody BugReportRequest reports) throws ExecutionException, InterruptedException {
        return ResponseEntity.status(201).body(bugReportService.takeReport(userDetails,reports).get());
    }

}
