package com.backend.stayEasy.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
	//ADMIN
	ADMIN_READ("admin:read"), 
	ADMIN_UPDATE("admin:update"), 
	ADMIN_CREATE("admin:create"), 
	ADMIN_DELETE("admin:delete"),
	
	//OWNER
	OWNER_READ("owner:read"), 
	OWNER_UPDATE("owner:update"), 
	OWNER_CREATE("owner:create"), 
	OWNER_DELETE("owner:delete");
	
	@Getter
	private final String permission;
}
