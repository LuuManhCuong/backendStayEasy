package com.backend.stayEasy.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="property_category")
public class PropertyCategory {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "property_category_id")
    private UUID propertyCategoryId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Property property;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Category category;
}