package com.backend.stayEasy.repository;

import com.backend.stayEasy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmail(String email);
	
	 // Phương thức để đếm số lượng tài khoản được tạo từ đầu tháng cho đến ngày hiện tại
    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    long countUsersCreatedBetween(Date startDate, Date endDate);

}
