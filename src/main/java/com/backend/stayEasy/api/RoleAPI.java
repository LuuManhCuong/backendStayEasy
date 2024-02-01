package com.backend.stayEasy.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.dto.RoleDTO;
import com.backend.stayEasy.entity.Role;
import com.backend.stayEasy.sevice.RoleService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleAPI {

	@Autowired
	private RoleService roleService;

	@PostMapping
	public ResponseEntity<?> createRole(@RequestBody Role role) {
		try {
			roleService.save(role);
			return ResponseEntity.ok(role);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<List<RoleDTO>> getAllRole(){
		return ResponseEntity.ok(roleService.getAllRoles());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RoleDTO> getRoleById(@PathVariable String id){
		return ResponseEntity.ok(roleService.getRoleById(UUID.fromString(id)));
	}
}
