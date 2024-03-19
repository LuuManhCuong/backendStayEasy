package com.backend.stayEasy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.stayEasy.entity.Feedback2;

@Repository
public interface Feedback2Repository extends JpaRepository<Feedback2, UUID>{
	List<Feedback2> findByPropertyPropertyId(UUID propertyId);
	Feedback2 findByPropertyPropertyIdAndUserId(UUID propertyId, UUID userId);
}
