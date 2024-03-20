package com.backend.stayEasy.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.repository.UserRepository;
import com.backend.stayEasy.sevice.AuthService;
import com.backend.stayEasy.sevice.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthAPI {
	
	@Value("${phone.api.key}")
	String phoneKeyAPI;
	
	@Value("${phone.api.url}")
	String phoneUrlAPI;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;
	
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
		String email = request.get("email");// email lấy từ body request
		
		// Kiểm tra xem email đã tồn tại trong hệ thống chưa
		if (userRepository.existsByEmail(email)) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "Email " + email + " đã đăng ký!");
			errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
			// Trả về thông báo lỗi khi email đã tồn tại
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(errorResponse);
		}
		
		//gửi code về email
		return authService.sendVerifyCodeToEmail(email);
	}
	
	/**
	 * 
	 * @author NamHH
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
		String email = request.get("email");// email lấy từ body request
		
		// Kiểm tra xem email đã tồn tại trong hệ thống không
		if (!userRepository.existsByEmail(email)) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "Email " + email + " chưa được đăng ký tài khoản nào!");
			errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
			// Trả về thông báo khi email đã tồn tại
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(errorResponse);
		}
		//gửi code về email
		return authService.sendVerifyCodeToEmail(email);
	}
	
	/**
	 * 
	 * @author NamHH
	 * @param changePasswordRequest
	 * @param request
	 * @return
	 */
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {

		String email = request.get("email");// email lấy từ body request
		
		String password = request.get("newPassword");

		Map<String, Object> response = new HashMap<>();
		UserDTO user = userService.resetPassword(email, password);
		
		response.put("message", "Đặ lại mật khẩu thành công!");
        response.put("user", user);
        response.put("status", HttpStatus.OK.value());
		
		// Cập nhật mật khẩu
		return ResponseEntity.ok(response);
	}
	
	/**
	 * 
	 * @author NamHH
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/verify-phone")
	public ResponseEntity<?> verifyPhone(@RequestBody Map<String, String> requestAPI) throws IOException {
		String phone = requestAPI.get("phone");// email lấy từ body request
		
		// Kiểm tra xem phone đã tồn tại trong hệ thống chưa
		if (userRepository.existsByPhone(phone)) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "Số " + phone + " đã liên kết với tài khoản khác!");
			errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
			// Trả về thông báo lỗi khi email đã tồn tại
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(errorResponse);
		}
		
		//Tạo 1 mã để xác thực
		int min = 100000; // Số nhỏ nhất có 6 chữ số
        int max = 999999; // Số lớn nhất có 6 chữ số
        Random random = new Random();
        String code = String.valueOf(random.nextInt((max - min) + 1) + min);
        
        Map<String, Object> responseAPI = new HashMap<>();
        
		try {
			// request to api send code to phone
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			
			okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
			
			@SuppressWarnings("deprecation")
			okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, 
					"{\"messages\":[{\"destinations\":["
					+ "{\"to\":\"84342531726\"},"
					+ "{\"to\":\""+ phone +"\"}],"
					+ "\"from\":\"StayEasy\","
					+ "\"text\":\"Mã xác thực của bạn là: "+ code +"\"}]}");
			
			Request request = new Request.Builder()
			    .url(phoneUrlAPI)
			    .method("POST", body)
			    .addHeader("Authorization", "App " + phoneKeyAPI)
			    .addHeader("Content-Type", "application/json")
			    .addHeader("Accept", "application/json")
			    .build();
			Thread.sleep(3000);
			Response response = client.newCall(request).execute();
	        
	        responseAPI.put("message", "Đã gửi mã. Hãy kiểm tra tin nhắn!");
	        responseAPI.put("code", code);
	        responseAPI.put("response", response);
	        responseAPI.put("status", HttpStatus.OK.value());
	        
	        return ResponseEntity.ok(responseAPI);
		} catch (Exception e) {
	        responseAPI.put("message", "Không gửi được mã!");
	        responseAPI.put("status", HttpStatus.BAD_REQUEST.value());
	        
	        return ResponseEntity.ok(responseAPI);
		}
	}
}