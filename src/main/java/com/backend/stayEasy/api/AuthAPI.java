package com.backend.stayEasy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.dto.SignUpRequest;
import com.backend.stayEasy.sevice.AuthService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthAPI {
	
	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody SignUpRequest request) {
		authService.register(request);
		return ResponseEntity.ok("Create OK");
	}

}
