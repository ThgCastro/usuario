package org.thgcastro.usuario.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.thgcastro.usuario.infrastructure.exceptions.ConflictException;
import org.thgcastro.usuario.infrastructure.exceptions.ResourceNotFoundException;
import org.thgcastro.usuario.infrastructure.exceptions.UnauthorizedException;
import org.thgcastro.usuario.infrastructure.exceptions.IllegalArgumentException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handlerResourceNotFoundException(ResourceNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handlerConflictException(ConflictException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handlerUnauthorizedException(UnauthorizedException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handlerIllegalArgumentException(IllegalArgumentException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
