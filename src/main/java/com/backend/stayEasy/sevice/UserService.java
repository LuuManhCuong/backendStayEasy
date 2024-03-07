package com.backend.stayEasy.sevice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.convertor.UserConverter;
import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.repository.UserRepository;

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
		for(User user: userRepository.findAll()) {
			result.add(new UserDTO(user.getId(),user.getEmail(),user.getFirstName(),user.getLastName(),null,user.getRole().name()));
		}
		return result;
	}

	public UserDTO getUserById(UUID id) {
		return userConverter.toDTO(userRepository.findUserById(id).get());
	}

	public UserDTO getUserByEmail(String email) {
		return userConverter.toDTO(userRepository.findByEmail(email).get());
	}

	public User save(User newUser) {
		if (newUser.getId() == null) {
			newUser.setCreatedAt(LocalDateTime.now());
		}

		newUser.setUpdatedAt(LocalDateTime.now());
		return userRepository.save(newUser);
	}
}