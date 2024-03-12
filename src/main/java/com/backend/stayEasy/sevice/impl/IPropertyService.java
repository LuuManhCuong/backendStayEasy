package com.backend.stayEasy.sevice.impl;

import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.Property;

import java.util.List;
import java.util.UUID;

public interface IPropertyService {
	
	List<PropertyDTO> findAll();

	PropertyDTO findById(UUID id);

	PropertyDTO add(PropertyDTO propertyDTO);

	PropertyDTO update(UUID propertyId, PropertyDTO updatePropertyDTO);

	Property delete(UUID propertyId);

<<<<<<< HEAD:src/main/java/com/backend/stayEasy/sevice/impl/IPropertyService.java
	List<PropertyDTO> findByCategory(UUID categoryId);
=======
    List<PropertyDTO> findAllPropertiesByHostId(UUID hostId);

//	List<Property> findByCategory(UUID categoryId);
>>>>>>> origin/loc-check-booking:src/main/java/com/backend/stayEasy/sevice/IPropertyService.java
	
}
