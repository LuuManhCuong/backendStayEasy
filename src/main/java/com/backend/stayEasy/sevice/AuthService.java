package com.backend.stayEasy.sevice;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.convertor.UserConverter;
import com.backend.stayEasy.dto.SignInRequest;
import com.backend.stayEasy.dto.SignInResponse;
import com.backend.stayEasy.dto.SignUpRequest;
import com.backend.stayEasy.entity.Token;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.enums.TokenType;
import com.backend.stayEasy.repository.TokenRepository;
import com.backend.stayEasy.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService  {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;


	public ResponseEntity<?> register(SignUpRequest request) {
		// Kiểm tra xem email đã tồn tại trong hệ thống chưa
        if (repository.existsByEmail(request.getEmail())) {
        	Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Email " + request.getEmail() + " đã đăng ký!");
            errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
            // Trả về thông báo lỗi khi email đã tồn tại
        	return ResponseEntity
        			.status(HttpStatus.BAD_REQUEST)
        			.contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }
        
		var user = User.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(request.getRole())
				.createdAt(Date.valueOf(LocalDate.now()))
				.updatedAt(Date.valueOf(LocalDate.now()))
				.build();
		var savedUser = repository.save(user);
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		saveUserToken(savedUser, jwtToken);
		return ResponseEntity.ok(SignInResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.user(userConverter.toDTO(user))
				.build());
	}

	public SignInResponse authenticate(SignInRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = repository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		return SignInResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.user(userConverter.toDTO(user))
				.build();
	}

	private void saveUserToken(User user, String jwtToken) {
		var token = Token.builder().user(user).token(jwtToken).tokenType(TokenType.BEARER).expired(false).revoked(false)
				.build();
		tokenRepository.save(token);
	}

	private void revokeAllUserTokens(User user) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userEmail;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		if (userEmail != null) {
			var user = this.repository.findByEmail(userEmail).orElseThrow();
			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, accessToken);
				var authResponse = SignInResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow();
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public SignInResponse changePassword(String username, String newPassword) {
        User user = userRepository.findByEmail(username).orElseThrow();
        user.setPassword(bcryptEncoder.encode(newPassword));
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
        return SignInResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.user(userConverter.toDTO(user))
				.build();
    }
}
