package org.kbannach.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ErrorResponse> handleEntityNotFoundException() {
        return ResponseEntity.badRequest().body(new ErrorResponse(ErrorType.ENTITY_NOT_FOUND, "No entity found."));
    }
}
