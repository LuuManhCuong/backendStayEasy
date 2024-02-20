package com.backend.stayEasy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.stayEasy.entity.Property;

@Repository
public interface ExploreRepository extends JpaRepository<Property, UUID> {

    List<Property> findAll();
    List<Property> findByPropertyNameContainingIgnoreCase(String keySearch); // Tìm kiếm trong trường propertyName
}
