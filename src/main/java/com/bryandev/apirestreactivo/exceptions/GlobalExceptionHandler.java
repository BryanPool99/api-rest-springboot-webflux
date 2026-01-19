package com.bryandev.apirestreactivo.exceptions;

import com.bryandev.apirestreactivo.model.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleUserNotFound(UserNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), "ERROR_USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleEmailExist(EmailAlreadyExistException ex) {
        return buildErrorResponse(ex.getMessage(), "ERROR_EMAIL_EXIST", HttpStatus.BAD_REQUEST);
    }

    // Método genérico para evitar repetición
    private Mono<ResponseEntity<ErrorResponse>> buildErrorResponse(String message, String code, HttpStatus status) {
        var error = ErrorResponse.builder()
                .mensaje(message)
                .codigo(code)
                .timestamp(LocalDateTime.now())
                .build();
        return Mono.just(ResponseEntity.status(status).body(error));
    }
}
