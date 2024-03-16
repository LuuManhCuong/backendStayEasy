package com.backend.stayEasy.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {
	ObjectMapper mapper = new ObjectMapper();
	Map<String, Object> errorResponse = new HashMap<>();

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {
		errorResponse.put("message", "Error!");
		errorResponse.put("status", HttpStatus.FORBIDDEN.value());

		httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
		mapper.writeValue(httpServletResponse.getOutputStream(), errorResponse);
	}

	@ExceptionHandler({ BadCredentialsException.class, AuthenticationCredentialsNotFoundException.class })
	public void handleBadCredentialsException(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Exception ex)
			throws IOException, ServletException, DatabindException, IOException {
		errorResponse.put("message", "Mật khẩu không đúng!");
		errorResponse.put("status", HttpStatus.FORBIDDEN.value());

		httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
		mapper.writeValue(httpServletResponse.getOutputStream(), errorResponse);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public void handleExpiredJwtException(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, NoSuchElementException ex)
			throws IOException, ServletException, DatabindException, IOException {
		errorResponse.put("message", "Token hết hạn hoặc không hợp lệ!");
		errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

		httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		mapper.writeValue(httpServletResponse.getOutputStream(), errorResponse);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public void handleNoSuchElementException(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, NoSuchElementException ex)
			throws IOException, ServletException, DatabindException, IOException {
		errorResponse.put("message", "Không tìm thấy dữ liệu!");
		errorResponse.put("status", HttpStatus.FORBIDDEN.value());

		httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
		mapper.writeValue(httpServletResponse.getOutputStream(), errorResponse);
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<?> handleDisabledException(DisabledException ex) {
		// Xử lý khi tài khoản người dùng bị vô hiệu hóa
		return ResponseEntity.status(403).body("Tài khoản của bạn đã bị vô hiệu hóa");
	}

	@ExceptionHandler(LockedException.class)
	public ResponseEntity<?> handleLockedException(LockedException ex) {
		// Xử lý khi tài khoản người dùng bị khóa
		return ResponseEntity.status(403).body("Tài khoản của bạn đã bị khóa");
	}

	@ExceptionHandler(InternalServerError.class)
	public ResponseEntity<?> handleDisabledException(InternalServerError ex) {
		return ResponseEntity.status(500).body("Lỗi 5 xị");
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDisabledException(DataIntegrityViolationException ex) {
		return ResponseEntity.status(500).body("Lỗi ");
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		// Xử lý khi không tìm thấy người dùng
		return ResponseEntity.badRequest().body("Không tìm thấy người dùng");
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Đường dẫn không tồn tại.");
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy đường dẫn này.");
	}

	@ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<String> handleMethodNotAllowedException(
			org.springframework.web.HttpRequestMethodNotSupportedException ex) {
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Phương thức HTTP không được hỗ trợ.");
	}
}