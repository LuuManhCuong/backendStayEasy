package com.backend.stayEasy.api;


import org.springframework.web.bind.annotation.CrossOrigin;


import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stayeasy")
@PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
public class mainApi {
	
	@GetMapping
	@PreAuthorize("hasAuthority('owner:read')")
	public String homePage() {
		return "WELCOME TO STAYEASY.";
	}
	
	@GetMapping("/alo")
	@PreAuthorize("hasAuthority('admin:read')")
	public String homePagetest() {
		return "WELCOME TO STAYEASY1.";
	}
}
