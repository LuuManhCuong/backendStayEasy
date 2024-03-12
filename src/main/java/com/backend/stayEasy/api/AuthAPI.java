package com.backend.stayEasy.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.dto.ChangePasswordRequest;
import com.backend.stayEasy.dto.SignInRequest;
import com.backend.stayEasy.dto.SignInResponse;
import com.backend.stayEasy.dto.SignUpRequest;
import com.backend.stayEasy.dto.TokenDTO;
import com.backend.stayEasy.sevice.AuthService;
import com.backend.stayEasy.sevice.JwtService;
import com.backend.stayEasy.sevice.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthAPI {

	private final AuthService service;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private AuthenticationManager authenticationManager;


	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody SignUpRequest request) {
		
		return service.register(request);
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
	
	@GetMapping("/checkLogin")
	public ResponseEntity<?> checkLogin(HttpServletRequest request) {
	    String token = request.getHeader("Authorization");
	    if (token != null && token.startsWith("Bearer ")) {
	        token = token.substring(7);
	        boolean isValid = jwtService.isTokenValid(token);
	        if (isValid) {
	            return ResponseEntity.ok().body("User is logged in.");
	        }
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
	}
	
	@PostMapping("/change-password")
    public ResponseEntity<SignInResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, HttpServletRequest request) {
        final String token = request.getHeader("Authorization").substring(7); // Lấy token từ Header

        final String username = userService.getUserByToken(token).getEmail(); // Lấy tên người dùng từ token

        // Xác thực người dùng
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, changePasswordRequest.getOldPassword()));

        // Cập nhật mật khẩu
//        service.changePassword(username, changePasswordRequest.getNewPassword());

        return ResponseEntity.ok(service.changePassword(username, changePasswordRequest.getNewPassword()));
    }
	
}
