package com.backend.stayEasy.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.config.ApplicationAuditAware;
import com.backend.stayEasy.dto.SignUpRequest;
import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.sevice.AuthService;
import com.backend.stayEasy.sevice.JwtService;
import com.backend.stayEasy.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/stayeasy/user")
public class UserAPI {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private ApplicationAuditAware applicationAuditAware;

	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUser() {
		return ResponseEntity.ok(userService.getAllUser());
	}

	@GetMapping("/token")
	public ResponseEntity<?> getUserByAccessToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
			System.out.println("hello");
			boolean isValid = jwtService.isTokenValid(token);
			if (isValid) {
				return ResponseEntity.ok(userService.getUserByToken(token));
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
	}

	@GetMapping("/{id}")
//	@PreAuthorize("hasAuthority('owner:read')")
	public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
		return ResponseEntity.ok(userService.getUserById(UUID.fromString(id)));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('admin:create')")
	public ResponseEntity<?> post(@RequestBody SignUpRequest request) {
		return authService.register(request);
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody UserDTO userDTO, HttpServletRequest request) {
		// Get người dùng đang đăng nhập hiện tại
		UUID currentUserId = applicationAuditAware.getCurrentAuditor().get();

		// Kiểm tra xem người dùng hiện tại có được phép thay đổi thông tin không?
		if (!currentUserId.equals(userDTO.getId())) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "Bạn không có quyền thay đổi thông tin!");
			errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
			// Trả về thông báo lỗi
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(errorResponse);
		}
		return ResponseEntity.ok(userService.save(userDTO));
	}

}
