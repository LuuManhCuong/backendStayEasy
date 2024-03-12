package com.backend.stayEasy.entity;

<<<<<<< HEAD
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
=======
>>>>>>> origin/namhh-update-infor
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Category")
public class Category {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id")
    private UUID categoryId;
	
	@Column(name = "category_name", columnDefinition = "nvarchar(255)")
	private String categoryName;
	
	@OneToMany(mappedBy = "category")
	private Set<PropertyCategory> propertyCategories;
	
//	@ManyToMany(mappedBy = "categories")
//	private Set<Property> properties = new HashSet<>();
}