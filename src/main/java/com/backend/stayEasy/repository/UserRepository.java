package com.backend.stayEasy.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.stayEasy.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);

	Optional<User> findUserById(UUID id);
	
	@Query("SELECT u FROM User u JOIN Token t ON u.id = t.user.id WHERE t.token = :token")
	Optional<User> findByToken(String token);
}
