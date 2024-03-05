package com.backend.stayEasy.api;

import com.backend.stayEasy.dto.TokenDTO;
import com.backend.stayEasy.sevice.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/token")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class TokenAPI {
	
	@Autowired
	private TokenService tokenService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('owner:read')")
	public ResponseEntity<List<TokenDTO>> getAllToken(){
		return ResponseEntity.ok(tokenService.getAllToken());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TokenDTO> getTokenById(@PathVariable String id){
		return ResponseEntity.ok(tokenService.getTokenById(UUID.fromString(id)));
	}
	
}
