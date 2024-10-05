package com.example.telemaapi.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AuthenticationResponse {
	private final String jwt;
	private final String username;
	private final Date expirationDate;

}
