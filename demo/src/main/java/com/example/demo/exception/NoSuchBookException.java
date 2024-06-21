package com.example.demo.exception;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 29.05.2024
 */
public class NoSuchBookException extends RuntimeException{
    public NoSuchBookException(String message) {
        super(message);
    }
}
