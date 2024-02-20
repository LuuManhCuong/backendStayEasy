package com.backend.stayEasy.enums;

import static com.backend.stayEasy.enums.Permission.ADMIN_CREATE;
import static com.backend.stayEasy.enums.Permission.ADMIN_DELETE;
import static com.backend.stayEasy.enums.Permission.ADMIN_READ;
import static com.backend.stayEasy.enums.Permission.ADMIN_UPDATE;
import static com.backend.stayEasy.enums.Permission.OWNER_CREATE;
import static com.backend.stayEasy.enums.Permission.OWNER_DELETE;
import static com.backend.stayEasy.enums.Permission.OWNER_READ;
import static com.backend.stayEasy.enums.Permission.OWNER_UPDATE;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

	USER(Collections.emptySet()), 
	ADMIN(Set.of(
			ADMIN_READ, 
			ADMIN_UPDATE, 
			ADMIN_DELETE, 
			ADMIN_CREATE,
			OWNER_READ)),
	OWNER(Set.of(
			OWNER_READ, 
			OWNER_UPDATE, 
			OWNER_CREATE, 
			OWNER_DELETE));

	@Getter
	private final Set<Permission> permissions;

	public List<SimpleGrantedAuthority> getAuthorities() {
		var authorities = getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}
}
