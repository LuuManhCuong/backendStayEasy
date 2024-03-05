package com.backend.stayEasy.api;

import java.util.List;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.dto.SignInResponse;
import com.backend.stayEasy.dto.SignUpRequest;
import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.sevice.AuthService;
import com.backend.stayEasy.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/stayeasy/user")
//@RequestMapping(value = "/api/v1/stayeasy/user", produces = "application/json")
public class UserAPI {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;

	@GetMapping
//	@PreAuthorize("hasAuthority('admin:read')")
	public ResponseEntity<List<UserDTO>> getAllUser() {
		return ResponseEntity.ok(userService.getAllUser());
	}

	@GetMapping("/{id}")
//	@PreAuthorize("hasAuthority('owner:read')")
	public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
		return ResponseEntity.ok(userService.getUserById(UUID.fromString(id)));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('admin:create')")
	public ResponseEntity<SignInResponse> post(@RequestBody SignUpRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@PutMapping
	public String put() {
		return "PUT:: user controller";
	}

	@DeleteMapping
	public String delete() {
		return "DELETE:: user controller";
	}

}
