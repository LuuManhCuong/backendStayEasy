package com.backend.stayEasy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.stayEasy.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

	Role findRoleByName(String name);
	
	Role findRoleById(UUID id);
}
