package com.backend.stayEasy.api;

import com.backend.stayEasy.dto.SignInRequest;
import com.backend.stayEasy.dto.SignInResponse;
import com.backend.stayEasy.dto.SignUpRequest;
import com.backend.stayEasy.dto.TokenDTO;
import com.backend.stayEasy.sevice.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthAPI {

	private final AuthService service;

	@PostMapping("/register")
	public ResponseEntity<SignInResponse> register(@RequestBody SignUpRequest request) {
		return ResponseEntity.ok(service.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<SignInResponse> authenticate(@RequestBody SignInRequest request) {
		return ResponseEntity.ok(service.authenticate(request));
	}

	@PostMapping("/refresh-token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		service.refreshToken(request, response);
	}
	
	@GetMapping("/token")
	public ResponseEntity<TokenDTO> getAllToken() {
		return ResponseEntity.ok(null);
	}
	
}
