package com.backend.stayEasy.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
	private UUID id;
	private boolean expired;
	private boolean revoked;
	private String token;
	private String type;
	private UUID userId;
}
