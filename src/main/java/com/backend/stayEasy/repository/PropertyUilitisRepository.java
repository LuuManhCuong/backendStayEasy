package com.backend.stayEasy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.stayEasy.entity.Property;

public interface PropertyUilitisRepository  extends JpaRepository<Property, UUID>{

}
