package com.backend.stayEasy.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name="Category")
public class Category {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id")
    private UUID categoryId;

	private String category_name;
	
	@OneToMany(mappedBy = "category")
	private Set<PropertyCategory> propertyCategories;
}
