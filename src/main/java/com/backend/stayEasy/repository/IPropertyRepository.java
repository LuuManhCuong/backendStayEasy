package com.backend.stayEasy.repository;



import java.sql.Date;
import java.util.List;
import java.util.UUID;


import com.backend.stayEasy.entity.Property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IPropertyRepository extends JpaRepository<Property, UUID>{

	Property findByPropertyId(UUID propertyId);
	List<Property> findByUserId(UUID userId);	
	@Query("SELECT p FROM Property p WHERE p.propertyId NOT IN ( " +
	           "SELECT DISTINCT b.property.propertyId FROM Booking b " +
	           "WHERE b.checkIn <= :endDate AND b.checkOut >= :startDate) " +
	           "AND LOWER(p.address) LIKE LOWER(CONCAT('%', :address, '%'))")
	List<Property> findAvailableProperties(Date startDate, Date endDate, String address);
	
	
	@Query("SELECT COUNT(p) FROM Property p WHERE p.createAt BETWEEN :startDate AND :endDate")
    long countPropertiesBetween(Date startDate, Date endDate);

}
