package com.backend.stayEasy.convertor;

import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.enums.Role;

@Component
public class UserConverter {

	public UserDTO toDTO(User user) {
		UserDTO result = new UserDTO();
		result.setId(user.getId());
		result.setEmail(user.getEmail());
		result.setFirstName(user.getFirstName());
		result.setLastName(user.getLastName());
<<<<<<< HEAD
=======
		result.setPhone(user.getPhone());
		result.setAddress(user.getAddress());
>>>>>>> origin/namhh-update-infor
		result.setAvatar(user.getAvatar());
		result.setRoleName(user.getRole().name());
		return result;
	}
	
	public User toEntity(User user, UserDTO userDTO) {
		if(userDTO.getFirstName()!=null) {
			user.setFirstName(userDTO.getFirstName());
		}
		if(userDTO.getLastName()!=null) {
			user.setLastName(userDTO.getLastName());
		}
		if(userDTO.getEmail()!=null) {
			user.setEmail(userDTO.getEmail());
		}
		if(userDTO.getPhone()!=null) {
			user.setPhone(userDTO.getPhone());
		}
		if(userDTO.getAddress()!=null) {
			user.setAddress(userDTO.getAddress());
		}
		if(userDTO.getAvatar()!=null) {
			user.setAvatar(userDTO.getAvatar());
		}
		if(userDTO.getRoleName()!=null) {
			user.setRole(Role.valueOf(userDTO.getRoleName()));
		}

		return user;
	}

}