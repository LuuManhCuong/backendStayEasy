package com.backend.stayEasy.sevice;

import java.util.List;
import java.util.UUID;

import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.Property;



public interface IPropertyService {
	List<PropertyDTO> findAll();
	PropertyDTO findById(UUID id);
	List<PropertyDTO> findByCategory(UUID categoryId);
	List<PropertyDTO> findByUserId(UUID userId);
	void deleteProperty(UUID id);
}
