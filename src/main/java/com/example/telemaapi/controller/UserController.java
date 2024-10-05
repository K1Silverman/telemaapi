package com.example.telemaapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.telemaapi.dto.UserDto;
import com.example.telemaapi.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User", description = "User API, for registering new users")
@RequestMapping("/api/public")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public String registerNewUser(UserDto userDto) {
		return "New user registered: " + userService.registerNewUser(userDto);
	}
}
