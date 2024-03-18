package com.backend.stayEasy.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckLoginResponseDTO {
	private String message;
	private boolean isLogin;
	private boolean isValid;
	private boolean isExist;
	private boolean isExpried;
	private UserDTO user;
}
