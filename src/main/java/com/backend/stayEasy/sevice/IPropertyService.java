package com.backend.stayEasy.sevice;

import java.util.List;
import java.util.UUID;

import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.Property;



public interface IPropertyService {
	List<PropertyDTO> findAll();
	PropertyDTO findById(UUID id);
	List<Property> findByCategory(UUID categoryId);
}
