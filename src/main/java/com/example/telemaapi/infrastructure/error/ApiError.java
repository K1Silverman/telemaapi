package com.example.telemaapi.infrastructure.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {
	private String error;
	private String message;
}
