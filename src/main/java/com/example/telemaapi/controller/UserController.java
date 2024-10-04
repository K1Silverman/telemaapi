package com.example.telemaapi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.telemaapi.dto.UserDto;
import com.example.telemaapi.service.UserService;

import jakarta.annotation.Resource;

@RestController
public class UserController {

	@Resource
	private UserService userService;

	@PostMapping("/register")
	public String registerNewUser(UserDto userDto) {
		return "New user registered: " + userService.registerNewUser(userDto);
	}

	@PostMapping("/login")
	public String authenticateUser(UserDto userDto) {
		return "User authenticated: " + userService.authenticateUser(userDto);
	}
}
