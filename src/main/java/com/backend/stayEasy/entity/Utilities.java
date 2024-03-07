package com.backend.stayEasy.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="utilities")
public class Utilities {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID utilitiId;
	
	@Column(name = "utilities_name", columnDefinition = "nvarchar(255)")
	private String utilitiesName;
	
	@OneToMany(mappedBy = "utilities",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PropertyUilitis> propertyUilitis;
}
