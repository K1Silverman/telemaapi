package com.example.telemaapi.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.telemaapi.entity.User;
import com.example.telemaapi.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.getUserByUsername(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User not found: " + username);
		}
		return new org.springframework.security.core.userdetails.User(
				user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
	}

}
