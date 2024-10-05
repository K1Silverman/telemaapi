package com.example.telemaapi.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.telemaapi.dto.AuthenticationResponse;
import com.example.telemaapi.dto.UserDto;
import com.example.telemaapi.infrastructure.error.ApiError;
import com.example.telemaapi.utils.JwtUtil;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	public ResponseEntity<?> authenticateUser(UserDto userDto) throws Exception {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiError.builder().error("Authorization failed").message("Invalid username or password").build());
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		Date expirationDate = jwtUtil.extractExpiration(jwt);

		return ResponseEntity.ok(new AuthenticationResponse(jwt, userDetails.getUsername(), expirationDate));
	}
}
