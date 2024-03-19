package com.backend.stayEasy.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.backend.stayEasy.entity.Mail;
import com.backend.stayEasy.repository.UserRepository;
import com.backend.stayEasy.sevice.AuthService;
import com.backend.stayEasy.sevice.UserService;
import com.backend.stayEasy.sevice.impl.IMailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthAPI {

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private IMailService mailService;

	@Autowired
	private UserRepository authRepository;
	
	/**
	 * 
	 * @author NamHH
	 * @param request
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody SignUpRequest request) {

		return authService.register(request);
	}

	/**
	 * 
	 * @author NamHH
	 * @param request
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<SignInResponse> authenticate(@RequestBody SignInRequest request) {
		return ResponseEntity.ok(authService.authenticate(request));
	}

	/**
	 * @author NamHH
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return authService.refreshToken(request, response);
	}

	/**
	 * @author NamHH
	 * @return
	 */
	@GetMapping("/token")
	public ResponseEntity<TokenDTO> getAllToken() {
		return ResponseEntity.ok(null);
	}

	/**
	 * 
	 * @author NamHH
	 * @param changePasswordRequest
	 * @param request
	 * @return
	 */
	@PostMapping("/change-password")
	public ResponseEntity<SignInResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
			HttpServletRequest request) {
		final String token = request.getHeader("Authorization").substring(7); // Lấy token từ Header

		final String username = userService.getUserByToken(token).getEmail(); // Lấy tên người dùng từ token

		// Xác thực người dùng
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, changePasswordRequest.getOldPassword()));

		// Cập nhật mật khẩu
		return ResponseEntity.ok(authService.changePassword(username, changePasswordRequest.getNewPassword()));
	}
	
	
	@PostMapping("/verify-email")
	public ResponseEntity<?> verifyEmail(@RequestBody Map<String, String> request) {
		// Kiểm tra xem email đã tồn tại trong hệ thống chưa
		if (authRepository.existsByEmail(request.get("email"))) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "Email " + request.get("email") + " đã đăng ký!");
			errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
			// Trả về thông báo lỗi khi email đã tồn tại
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(errorResponse);
		}
		
		String subject = "Xác minh Email";
		String code = request.get("code");
        // set new mail
        Mail mail = new Mail();
        mail.setRecipient(request.get("email"));
        mail.setSubject(subject);
        mail.setContent(code);
        mailService.sendEmailVerify(mail, code);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Đã gửi mã tới email. Hãy kiểm tra email!");
        response.put("status", HttpStatus.OK.value());
        
        return ResponseEntity.ok(response);
	}

}
