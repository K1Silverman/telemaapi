package com.example.telemaapi.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.example.telemaapi.infrastructure.error.ApiError;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ApiError> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ApiError.builder().error("Validation Error").message("File type not allowed").build());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ApiError.builder().error("Validation error").message("File type not allowed").build());
	}
}
