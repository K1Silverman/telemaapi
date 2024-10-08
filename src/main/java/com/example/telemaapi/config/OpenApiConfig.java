package com.example.telemaapi.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	public GroupedOpenApi customApi() {
		return GroupedOpenApi.builder()
				.group("api")
				.pathsToMatch("/api/**")
				.pathsToExclude("/api/profile/**", "/api/user-entity/**", "/api/user-search/**")
				.build();
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList("Bearer Token"))
				.components(new Components()
						.addSecuritySchemes(
								"Bearer Token",
								new SecurityScheme()
										.type(SecurityScheme.Type.HTTP)
										.scheme("bearer")
										.bearerFormat("JWT")));
	}
}
