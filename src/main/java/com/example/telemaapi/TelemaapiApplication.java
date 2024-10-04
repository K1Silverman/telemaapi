package com.example.telemaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication()
@OpenAPIDefinition(info = @Info(title = "Telema API", version = "1.0", description = "Telema API for file uploading"))
public class TelemaapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelemaapiApplication.class, args);
	}

}
