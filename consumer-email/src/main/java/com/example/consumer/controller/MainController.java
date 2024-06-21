package com.example.consumer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * consumer
 * Author: Vasylenko Oleksii
 * Date: 15.06.2024
 */
@RestController
public class MainController {

    @PostMapping("/")
    public ResponseEntity<?> listener() {
        return ResponseEntity.ok("Hello Email!");
    }
}
