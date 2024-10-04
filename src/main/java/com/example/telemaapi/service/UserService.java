package com.example.telemaapi.service;

import org.springframework.stereotype.Service;

import com.example.telemaapi.dto.UserDto;
import com.example.telemaapi.entity.User;
import com.example.telemaapi.mapper.UserMapper;
import com.example.telemaapi.repository.UserRepository;
import com.example.telemaapi.utils.PasswordUtils;

import jakarta.annotation.Resource;

@Service
public class UserService {

	@Resource
	private UserRepository userRepository;

	@Resource
	private UserMapper userMapper;

	public String registerNewUser(UserDto userDto) {
		User user = userMapper.toEntity(userDto);
		String hashedPassword = PasswordUtils.hashPassword(userDto.getPassword());
		user.setPassword(hashedPassword);
		userRepository.save(user);
		return user.getUsername();
	}

	public boolean authenticateUser(UserDto userDto) {
		User user = userRepository.findByUsername(userDto.getUsername());
		if (user == null) {
			return false;
		}
		return PasswordUtils.checkPassword(userDto.getPassword(), user.getPassword());
	}
}
