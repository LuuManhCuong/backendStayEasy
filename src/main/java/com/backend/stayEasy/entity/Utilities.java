package com.backend.stayEasy.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Utilities {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID propertyUtilitiId;
	
	@OneToMany(mappedBy = "utilities")
	private Set<PropertyUilitis> propertyUilitis;
}
