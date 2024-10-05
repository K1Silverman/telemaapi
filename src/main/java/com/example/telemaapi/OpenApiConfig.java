package com.example.telemaapi;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
