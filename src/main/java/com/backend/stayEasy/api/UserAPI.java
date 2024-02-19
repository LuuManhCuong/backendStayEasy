package com.backend.stayEasy.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.repository.RoleRepository;
import com.backend.stayEasy.sevice.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserAPI {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUser() {
		return ResponseEntity.ok(userService.getAllUser());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
		return ResponseEntity.ok(userService.getUserById(UUID.fromString(id)));
	}

}