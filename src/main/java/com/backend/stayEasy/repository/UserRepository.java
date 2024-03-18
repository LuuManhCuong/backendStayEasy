package com.backend.stayEasy.repository;

import java.sql.Date;
import java.time.LocalDateTime;
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

	// Phương thức để đếm số lượng tài khoản được tạo từ đầu tháng cho đến ngày hiện
	// tại
	@Query("SELECT COUNT(u) FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
	long countUsersCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);

	@Query("SELECT u FROM User u JOIN Token t ON u.id = t.user.id WHERE t.token = :token AND t.expired = false")
	Optional<User> findByToken(String token);
}
