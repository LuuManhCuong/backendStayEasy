package com.backend.stayEasy.sevice;

import com.backend.stayEasy.convertor.UserConverter;

import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

<<<<<<< HEAD
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.convertor.UserConverter;
import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

>>>>>>> origin/namhh-update-infor
@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserConverter userConverter;

	public List<UserDTO> getAllUser() {
		List<UserDTO> result = new ArrayList<>();
		for(User user: userRepository.findAll()) {
			result.add(new UserDTO(user.getId(),user.getEmail(),user.getFirstName(),user.getLastName(),user.getPhone(),user.getAddress(), null, user.getRole().name()));
		}
		return result;
	}

	public UserDTO getUserById(UUID id) {
		return userConverter.toDTO(userRepository.findById(id).get());
	}
	public UserDTO getUserByEmail(String email) {
		return userConverter.toDTO(userRepository.findByEmail(email).get());
	}
	
	public UserDTO getUserByToken(String token) {
		return userConverter.toDTO(userRepository.findByToken(token).get());
	}

<<<<<<< HEAD
	public User save(User newUser) {
		 // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();
     // Chuyển đổi từ LocalDate sang Date
        Date date = Date.valueOf(currentDate);
        
		if (newUser.getId() == null) {
			newUser.setCreatedAt(date);
		}

		newUser.setUpdatedAt(date);
		return userRepository.save(newUser);
=======
	@Transactional
	public UserDTO save(UserDTO userDTO) {
		User user = new User();
		if (userDTO.getId() != null) {
			User oldUser = userRepository.findUserById(userDTO.getId()).get();
			user = userConverter.toEntity(oldUser, userDTO);
			user.setUpdatedAt(LocalDateTime.now());
		}
		user.setUpdatedAt(LocalDateTime.now());
		return userConverter.toDTO(userRepository.save(user));
>>>>>>> origin/namhh-update-infor
	}
}