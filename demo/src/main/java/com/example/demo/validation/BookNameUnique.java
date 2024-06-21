package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Constraint(validatedBy = BookNameUniqueValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BookNameUnique {

    String message() default "A book with such name already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
