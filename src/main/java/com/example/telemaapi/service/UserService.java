package com.example.telemaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.telemaapi.dto.UserDto;
import com.example.telemaapi.entity.User;
import com.example.telemaapi.mapper.UserMapper;
import com.example.telemaapi.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String registerNewUser(UserDto userDto) {
		User user = userMapper.toEntity(userDto);
		String hashedPassword = passwordEncoder.encode(userDto.getPassword());
		user.setPassword(hashedPassword);
		userRepository.save(user);
		return user.getUsername();
	}
}
