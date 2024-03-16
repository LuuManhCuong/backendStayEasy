package com.backend.stayEasy.convertor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.enums.Role;

@Component
public class UserConverter {

	@Autowired
	private AddressConverter addressConverter;

	public UserDTO toDTO(User user) {
		UserDTO result = new UserDTO();
		result.setId(user.getId());
		result.setEmail(user.getEmail());
		result.setFirstName(user.getFirstName());
		result.setLastName(user.getLastName());
		result.setPhone(user.getPhone());
		if (user.getAddress() != null) {
			result.setAddress(addressConverter.toDTO(user.getAddress()));
		}
		result.setAvatar(user.getAvatar());
		result.setRoleName(user.getRole().name());
		result.setCreateAt(user.getCreatedAt());
		result.setUpdateAt(user.getUpdatedAt());
		return result;
	}

	public User toEntity(User user, UserDTO userDTO) {
		user.setFirstName(Optional.ofNullable(userDTO.getFirstName()).orElse(user.getFirstName()));
		user.setLastName(Optional.ofNullable(userDTO.getLastName()).orElse(user.getLastName()));
		user.setEmail(Optional.ofNullable(userDTO.getEmail()).orElse(user.getEmail()));
		user.setPhone(Optional.ofNullable(userDTO.getPhone()).orElse(user.getPhone()));
		user.setAddress(
				userDTO.getAddress() != null ? addressConverter.toEntity(userDTO.getAddress()) : user.getAddress());
		user.setAvatar(Optional.ofNullable(userDTO.getAvatar()).orElse(user.getAvatar()));
		user.setRole(userDTO.getRoleName() != null ? Role.valueOf(userDTO.getRoleName()) : user.getRole());

		return user;
	}

}