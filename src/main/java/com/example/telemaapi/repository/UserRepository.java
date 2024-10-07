package com.example.telemaapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.telemaapi.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	@Query("select u from User u where u.username = ?1")
	Optional<User> getUserByUsername(String username);

}
