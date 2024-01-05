package ru.namazov.ibetyouwill.config.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.namazov.ibetyouwill.exceptions.IllegalArgumentException;
import ru.namazov.ibetyouwill.exceptions.NotFoundException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler {

    private record ExceptionResponse(List<String> errors) {
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse illegalArgument(final IllegalArgumentException ex) {
        return new ExceptionResponse(List.of(ex.getMessage()));
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse notFound(final NotFoundException ex) {
        return new ExceptionResponse(List.of(ex.getMessage()));
    }
}
