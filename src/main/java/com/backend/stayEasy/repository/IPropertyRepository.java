package com.backend.stayEasy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.stayEasy.entity.Property;

@Repository
public interface IPropertyRepository extends JpaRepository<Property, UUID>{
	
	@Query("SELECT DISTINCT p FROM Property p "
		       + "LEFT JOIN FETCH p.likes "
		       + "LEFT JOIN FETCH p.feedbacks "
		       + "LEFT JOIN FETCH p.images "
		       + "LEFT JOIN FETCH p.bookingDetails "
		       + "LEFT JOIN FETCH p.propertyCategories "
		       + "LEFT JOIN FETCH p.propertyUilitis")
		List<Property> findAllPropertiesWithSets();

}
