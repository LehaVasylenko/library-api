package com.example.demo.mapper;

import com.example.demo.exception.ForbiddenAccessException;
import com.example.demo.exception.NoSuchBookException;
import com.example.demo.exception.TokenExpiredException;
import com.example.demo.exception.TooManyBooksException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public class ExceptionStatusMapper {

    public static HttpStatus determineHttpStatus(Exception ex) {
        if (getClassForSwitch(ex).equals(DataIntegrityViolationException.class) || getClassForSwitch(ex).equals(SQLException.class)) {
            return HttpStatus.CONFLICT;
        } else if (getClassForSwitch(ex).equals(NoSuchElementException.class) || getClassForSwitch(ex).equals(NoSuchBookException.class) || getClassForSwitch(ex).equals(NoHandlerFoundException.class)) {
            return HttpStatus.NOT_FOUND;
        } else if (getClassForSwitch(ex).equals(ForbiddenAccessException.class) || getClassForSwitch(ex).equals(TokenExpiredException.class) || getClassForSwitch(ex).equals(ExpiredJwtException.class)) {
            return HttpStatus.FORBIDDEN;
        } else if (getClassForSwitch(ex).equals(HttpMessageNotReadableException.class) || getClassForSwitch(ex).equals(MethodArgumentNotValidException.class) || getClassForSwitch(ex).equals(ConstraintViolationException.class)) {
            return HttpStatus.BAD_REQUEST;
        } else if (getClassForSwitch(ex).equals(TooManyBooksException.class)) {
            return HttpStatus.TOO_MANY_REQUESTS;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private static Class<?> getClassForSwitch(Exception ex) {
        return ex.getClass();
    }
}
