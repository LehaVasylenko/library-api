package com.example.demo.exception;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 29.05.2024
 */
public class TooManyBooksException extends RuntimeException{
    public TooManyBooksException(String message) {
        super(message);
    }
}
