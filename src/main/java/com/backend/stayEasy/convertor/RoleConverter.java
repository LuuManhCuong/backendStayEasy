package com.backend.stayEasy.convertor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.RoleDTO;
import com.backend.stayEasy.entity.Role;

@Component
public class RoleConverter {
	
	public RoleDTO toDTO(Role role) {
		RoleDTO result = new RoleDTO();
		
		List<UUID> userIds = new ArrayList<>();
		role.getUsers().forEach(u->userIds.add(u.getId()));
		
		result.setId(role.getId());
		result.setName(role.getName());
		result.setUserId(userIds);
		return result;
	}
}