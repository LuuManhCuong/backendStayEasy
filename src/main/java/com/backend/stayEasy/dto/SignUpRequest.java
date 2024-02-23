package com.backend.stayEasy.dto;

import com.backend.stayEasy.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private Role role;
}
