package com.backend.stayEasy.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.stayEasy.entity.Property;

@Repository
public interface IPropertyRepository extends JpaRepository<Property, UUID>{


	Property findByPropertyId(UUID propertyId);
	

}
