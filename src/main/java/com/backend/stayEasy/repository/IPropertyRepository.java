package com.backend.stayEasy.repository;

import com.backend.stayEasy.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IPropertyRepository extends JpaRepository<Property, UUID>{
	

}
