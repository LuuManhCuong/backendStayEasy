package com.backend.stayEasy.sevice;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.convertor.UserConverter;
import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConverter userConverter;

	public List<UserDTO> getAllUser() {
		List<UserDTO> result = new ArrayList<>();
		for (User user : userRepository.findAll()) {
			result.add(new UserDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(),
					user.getPhone(), user.getAddress(), null, user.getRole().name()));
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
		return userConverter.toDTO(userRepository.findByToken(token).orElseThrow());
	}

	@Transactional
	public UserDTO save(UserDTO userDTO) {
		User user = new User();
		if (userDTO.getId() != null) {
			User oldUser = userRepository.findUserById(userDTO.getId()).get();
			user = userConverter.toEntity(oldUser, userDTO);
			user.setUpdatedAt(Date.valueOf(LocalDate.now()));
		}
		user.setUpdatedAt(Date.valueOf(LocalDate.now()));
		return userConverter.toDTO(userRepository.save(user));
	}
}