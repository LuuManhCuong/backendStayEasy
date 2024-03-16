package com.backend.stayEasy.sevice;

<<<<<<< HEAD
=======
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

>>>>>>> origin/namhh-refresh-token
import com.backend.stayEasy.convertor.UserConverter;
import com.backend.stayEasy.dto.SignInRequest;
import com.backend.stayEasy.dto.SignInResponse;
import com.backend.stayEasy.dto.SignUpRequest;
import com.backend.stayEasy.entity.Token;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.enums.TokenType;
import com.backend.stayEasy.repository.TokenRepository;
import com.backend.stayEasy.repository.UserRepository;
<<<<<<< HEAD
import com.fasterxml.jackson.databind.ObjectMapper;
=======

>>>>>>> origin/namhh-refresh-token
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

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
	
	@Value("${token.expiration}")
	Long jwtExpirationMs;
	
	@Value("${refresh.token.expiration}")
	Long jwtRefreshExpirationMs;

	/**
	 * 
	 * @author NamHH
	 * @param request
	 * @return SignInResponse(accessToken, refreshToken, user)
	 */
	public ResponseEntity<?> register(SignUpRequest request) {
		// Kiểm tra xem email đã tồn tại trong hệ thống chưa
<<<<<<< HEAD
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
        // Lấy ngày hiện tại
	    LocalDate currentDate = LocalDate.now();
	    
	    // Chuyển đổi ngày hiện tại sang kiểu java.sql.Date
	    Date todayDate = Date.valueOf(currentDate);
		var user = User.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(request.getRole())
				.createdAt(todayDate)
				.updatedAt(todayDate)
				.build();
		var savedUser = repository.save(user);
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		saveUserToken(savedUser, jwtToken);
=======
		if (repository.existsByEmail(request.getEmail())) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "Email " + request.getEmail() + " đã đăng ký!");
			errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
			// Trả về thông báo lỗi khi email đã tồn tại
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(errorResponse);
		}
		
		//lấy và giá trị từ client gửi lên vào obj user mới
		var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
				.role(request.getRole()).createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now()).build();
		
		var savedUser = repository.save(user); //lưu user vào db
		var jwtToken = jwtService.generateToken(user); //Tạo accessToken
		var refreshToken = jwtService.generateRefreshToken(user); //Tạo refreshToken
		saveUserToken(savedUser, jwtToken, refreshToken); //lưu token vào db
>>>>>>> origin/namhh-refresh-token
		return ResponseEntity.ok(SignInResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.user(userConverter.toDTO(user))
				.message("Đăng nhập thành công!")
				.build());
	}

	/**
	 * 
	 * @author NamHH
	 * @param request
	 * @return SignInResponse(accessToken, refreshToken, user)
	 */
	public SignInResponse authenticate(SignInRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = repository.findByEmail(request.getEmail()).orElseThrow();
		
		var jwtToken = jwtService.generateToken(user); //Tạo refreshToken
		var refreshToken = jwtService.generateRefreshToken(user); //lưu token vào db
		
		//Tìm và hủy tất cả các token đang hoạt động của user này
		//(đảm bảo chỉ duy nhất 1 token hoạt động)
		revokeAllUserTokens(user);
		
		saveUserToken(user, jwtToken, refreshToken); //Lưu token mới vào db
		return SignInResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).user(userConverter.toDTO(user))
				.build();
	}

	/**
	 * 
	 * @author NamHH
	 * @param user
	 * @param jwtToken
	 * @param refreshToken
	 */
	private void saveUserToken(User user, String jwtToken, String refreshToken) {
		var token = Token.builder()
				.user(user)
				.tokenType(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.token(jwtToken)
				.refreshToken(refreshToken)
				.expirationTokenDate(LocalDateTime.now().plusSeconds(jwtExpirationMs/1000))//1day = 24*60*60
				.expirationRefTokenDate(LocalDateTime.now().plusSeconds(jwtRefreshExpirationMs/1000))//3day = 3*24*60*60
				.build();
		tokenRepository.save(token);
	}

	/**
	 * 
	 * @author NamHH
	 * @param user
	 * 
	 */
	private void revokeAllUserTokens(User user) {
		//Tìm tất cả các token bằng user_id
		var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			//Hủy các token tìm được
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);//lưu lại vào db
	}

	/**
	 * 
	 * @author NamHH
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//Lấy giá trị của header Authorization từ request.
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		final String refreshToken;
		final String userEmail;
		
		//Kiểm tra xem header Authorization có tồn tại và có bắt đầu bằng chuỗi "Bearer " không.
		//Nếu không tồn tại hoặc không bắt đầu bằng "Bearer ", phương thức trả về null
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return null;
		}
		//Bỏ "Bearer " trong chuỗi lấy được từ header
		refreshToken = authHeader.substring(7);
		
		//Get userName từ refreshToken 
		userEmail = jwtService.extractUsername(refreshToken);
		if (userEmail != null) {
			//Tìm kiếm người dùng trong cơ sở dữ liệu dựa trên email. Nếu không tìm thấy người dùng, phương thức sẽ ném một ngoại lệ.
			var user = this.repository.findByEmail(userEmail).orElseThrow();
			//Check refreshToken đó có hợp lệ không
			if (jwtService.isTokenValid(refreshToken, user)) {
				var newAccessToken = jwtService.generateToken(user);//Tạo token mới
				var newRefreshToken = jwtService.generateRefreshToken(user);//tạo refreshToken mới
				
				revokeAllUserTokens(user);//Tìm và hủy tất cả các token đang hoạt động của user này
				
				saveUserToken(user, newAccessToken, newRefreshToken);//lưu token vào db
				
				return ResponseEntity.ok(SignInResponse.builder()
						.accessToken(newAccessToken)
						.refreshToken(newRefreshToken)
						.user(userConverter.toDTO(user))
						.message("Làm mới token thành công!")
						.build());
			}
		}
		return null;
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

	/**
	 * 
	 * @author namHH
	 * @param username
	 * @param newPassword
	 * @return
	 */
	public SignInResponse changePassword(String username, String newPassword) {
		//Tìm kiếm người dùng trong cơ sở dữ liệu dựa trên email.
		//Nếu không tìm thấy người dùng, ném một NoSuchElementException.
		User user = userRepository.findByEmail(username).orElseThrow();
		
		//Thiết lập mật khẩu mới cho người dùng, sau khi mã hóa mật khẩu mới.
		user.setPassword(bcryptEncoder.encode(newPassword));
		userRepository.save(user);//lưu user vào db
		
		var jwtToken = jwtService.generateToken(user);//tạo accesstoken mới
		var refreshToken = jwtService.generateRefreshToken(user);//tạo refreshToken mới
		
		revokeAllUserTokens(user);//Tìm và hủy tất cả các token đang hoạt động của user này
		
		saveUserToken(user, jwtToken, refreshToken);//lưu token vào db
		return SignInResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.user(userConverter.toDTO(user))
				.message("Đổi mật khẩu thành công!")
				.build();
	}
}
