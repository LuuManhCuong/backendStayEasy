package com.backend.stayEasy.convertor;

import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.entity.User;

@Component
public class UserConverter {

	public UserDTO toDTO(User user) {
		UserDTO result = new UserDTO();
		result.setId(user.getId());
		result.setEmail(user.getEmail());
		result.setFirstName(user.getFirstName());
		result.setLastName(user.getLastName());
		result.setAvatar(user.getAvatar());
		result.setRoleName(user.getRole().name());
		return result;
	}

}