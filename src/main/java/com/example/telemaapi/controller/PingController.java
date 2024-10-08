package com.example.telemaapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Ping Controller", description = "Checking if services are up and running")
@RequestMapping("/api/public")
public class PingController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@GetMapping("/ping")
	@Operation(summary = "Ping", description = "Just returns Pong.")
	public String ping() {
		return "pong";
	}

	@GetMapping("/test-postgres")
	@Operation(summary = "Test Postgres", description = "Cheks if application has connection and access to database.")
	public String testPostgres() {
		try {
			String result = jdbcTemplate.queryForObject("SELECT 1", String.class);
			return "Success: " + result;
		} catch (DataAccessException e) {
			return "Error: " + e.getMessage();
		}
	}

	@GetMapping("/test-redis")
	@Operation(summary = "Test Redis", description = "Cheks if redis server is running and has connection.")
	public String testRedis() {
		try {
			redisTemplate.opsForValue().set("test", "Hello, Redis!");
			String result = redisTemplate.opsForValue().get("test");
			return "Success: " + result;
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}
}
