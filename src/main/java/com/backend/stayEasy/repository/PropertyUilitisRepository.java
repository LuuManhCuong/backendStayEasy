package com.backend.stayEasy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.stayEasy.entity.PropertyUilitis;



public interface PropertyUilitisRepository  extends JpaRepository<PropertyUilitis, UUID>{
	List<PropertyUilitis> findByPropertyPropertyId(UUID propertyId);
}
