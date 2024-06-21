package com.example.demo.validation;

import com.example.demo.repository.BookRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookNameUniqueValidator implements ConstraintValidator<BookNameUnique, String> {

    BookRepository bookRepository;

    @SneakyThrows
    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        return bookRepository.findByTitle(title).isEmpty();
    }
}
