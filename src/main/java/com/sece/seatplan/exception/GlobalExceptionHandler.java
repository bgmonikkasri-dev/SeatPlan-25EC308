package com.sece.seatplan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<String> handleNotFound(
        ResourceNotFoundException exception) {

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exception.getMessage());
}

@ExceptionHandler(CapacityExceededException.class)
public ResponseEntity<String> handleCapacity(
        CapacityExceededException exception) {

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception.getMessage());
}

@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<String> handleBadRequest(
        IllegalArgumentException exception) {

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception.getMessage());
}

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<String> handleValidation(
        MethodArgumentNotValidException exception) {

    StringBuilder message =
            new StringBuilder("Invalid input: ");

    for (FieldError error :
            exception.getBindingResult().getFieldErrors()) {

        message.append(error.getField())
                .append(" ")
                .append(error.getDefaultMessage())
                .append("; ");
    }

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(message.toString());
}

@ExceptionHandler(Exception.class)
public ResponseEntity<String> handleGeneralException(
        Exception exception) {

    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                    "An unexpected error occurred: "
                            + exception.getMessage()
            );
}


}
