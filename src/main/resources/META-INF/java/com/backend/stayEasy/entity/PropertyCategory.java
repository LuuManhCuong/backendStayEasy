package com.backend.stayEasy.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name="Property_category")
public class PropertyCategory {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "property_category_id")
    private UUID propertyCategoryId;

	@ManyToOne()
	private Property property;
	
	@ManyToOne()
	private Category category;
}
