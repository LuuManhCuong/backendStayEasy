package com.backend.stayEasy.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stayeasy")
public class mainApi {
	
	@GetMapping
	public String homePage() {
		return "WELCOME TO STAYEASY.";
	}
}
