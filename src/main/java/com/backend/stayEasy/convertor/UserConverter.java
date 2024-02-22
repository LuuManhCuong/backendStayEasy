package com.backend.stayEasy.convertor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.entity.User;

@Component
public class UserConverter {
	
	public UserDTO toDTO(User user) {
		UserDTO result = new UserDTO();
		
		List<String> roleNames = new ArrayList<>();
		user.getRoles().forEach(r->roleNames.add(r.getName()));
		
		result.setId(user.getId());
		result.setEmail(user.getEmail());
		result.setFirstName(user.getFirstName());
		result.setLastName(user.getLastName());
		result.setAvatar(user.getAvatar());
		result.setRoles(roleNames);
		return result;
	}
	
}