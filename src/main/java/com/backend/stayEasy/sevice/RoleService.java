package com.backend.stayEasy.sevice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.convertor.RoleConverter;
import com.backend.stayEasy.dto.RoleDTO;
import com.backend.stayEasy.entity.Role;
import com.backend.stayEasy.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleConverter roleConverter;

	public List<RoleDTO> getAllRoles() {
		List<RoleDTO> result = new ArrayList<>();
		
		for (Role role : roleRepository.findAll()) {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO = roleConverter.toDTO(role);
			result.add(roleDTO);
		}
		return result;
	}

	public RoleDTO getRoleById(UUID id) {
		return roleConverter.toDTO(roleRepository.findRoleById(id));
	}

	public Role getRoleByName(String name) {
		return roleRepository.findRoleByName(name);
	}

	public Role save(Role role) {
		
		System.out.println("role1: "+ role);
		if (role.getId() == null) {
			role.setCreatedAt(LocalDateTime.now());
		}
		role.setId(UUID.randomUUID());

		role.setUpdatedAt(LocalDateTime.now());
		System.out.println("role 2: "+ role);
		return roleRepository.save(role);
	}
}
