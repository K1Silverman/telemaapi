package com.example.telemaapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.telemaapi.dto.UserDto;
import com.example.telemaapi.service.AuthenticationService;
import com.example.telemaapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User", description = "Registration and authentication")
@RequestMapping("/api/public")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/register")
	@Operation(summary = "User registration", description = "Registers new user.")
	public String registerNewUser(UserDto userDto) {
		return "New user registered: " + userService.registerNewUser(userDto);
	}

	@PostMapping("/authenticate")
	@Operation(summary = "User authentication", description = "Authenticates user.")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto userDto) throws Exception {
		return authenticationService.authenticateUser(userDto);
	}
}
