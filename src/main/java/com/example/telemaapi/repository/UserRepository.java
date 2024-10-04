package com.example.telemaapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.telemaapi.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	User findByUsername(String username);

}
