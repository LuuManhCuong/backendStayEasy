package com.backend.stayEasy.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationExceptionHandler implements AuthenticationEntryPoint {

	
	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        Map<String, Object> errorResponse = new HashMap<>();
        if (ExpiredJwtException.class.getName().contains("ExpiredJwtException")) {
//        	System.out.println("loi: "+authException.getClass());
        	
            // Token hết hạn
            errorResponse.put("message", "Token đã hết hạn!");
            errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
        } else {
//        	System.out.println("loi: "+ExpiredJwtException.class.getName());
        	
            // Lỗi xác thực không được ủy quyền
            errorResponse.put("message", "Token không hợp lệ hoặc không được ủy quyền!");
            errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper mapper = new ObjectMapper();
        
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}