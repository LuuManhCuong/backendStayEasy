package com.backend.stayEasy.sevice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.dto.SignUpRequest;
import com.backend.stayEasy.entity.Role;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;

	public void register(SignUpRequest request) {
		List<Role> roles = new ArrayList<>();
		for (UUID id : request.getRoleIds()) {
			roles.add(roleRepository.findRoleById(id));
		}
		var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
				.email(request.getEmail()).password(request.getPassword()).roles(roles).build();

		userService.save(user);
	}
}
