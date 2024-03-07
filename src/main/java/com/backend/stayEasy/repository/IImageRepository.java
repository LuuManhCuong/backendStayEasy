package com.backend.stayEasy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.stayEasy.entity.Images;



public interface IImageRepository extends JpaRepository<Images, UUID>{
	List<Images> findByPropertyPropertyId(UUID propertyId);
	void deleteByPropertyPropertyId(UUID propertyId);
}
