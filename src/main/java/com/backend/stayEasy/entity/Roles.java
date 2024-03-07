package com.backend.stayEasy.entity;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Roles {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
    private UUID roleId;
	private String roleName;
	private String code;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<User> users;
}
