package com.backend.stayEasy.dto;

import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private UUID id;
	private String email;
	private String firstName;
	private String lastName;

	private String avatar;

	private String roleName;
}
