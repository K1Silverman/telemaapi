package com.example.telemaapi.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.example.telemaapi.infrastructure.error.ApiError;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ HandlerMethodValidationException.class, ConstraintViolationException.class })
	public ResponseEntity<ApiError> handleValidationExceptions(Exception e) {
		List<String> errors = new ArrayList<>();

		if (e instanceof HandlerMethodValidationException hmve) {
			hmve.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
		} else if (e instanceof ConstraintViolationException cve) {
			cve.getConstraintViolations().forEach(violation -> errors.add(violation.getMessage()));
		}

		String errorMessage = errors.isEmpty() ? "Validation failed" : String.join(", ", errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ApiError.builder()
						.error("Validation Error")
						.message(errorMessage)
						.build());
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ApiError> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
		String message = "File size exceeds the maximum allowed size (10MB)";

		return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
				.body(ApiError.builder().error("File too large").message(message).build());
	}
}
