package com.example.telemaapi.infrastructure;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.telemaapi.infrastructure.error.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		logger.error("Unauthorized error: ", authException.getMessage());

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ApiError body = ApiError.builder().error("Unauthorized").message(authException.getMessage()).build();
		objectMapper.writeValue(response.getOutputStream(), body);
	}
}