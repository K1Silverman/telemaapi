package com.example.telemaapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.telemaapi.infrastructure.CustomAuthenticationEntryPoint;
import com.example.telemaapi.utils.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private CustomAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/api/auth/**", "/api/public/**", "/swagger-ui/**", "v3/api-docs/**", "/swagger-ui.html",
								"/webjars/**")
						.permitAll()
						.anyRequest().authenticated())
				.exceptionHandling(exc -> exc.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
