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


	List<PropertyDTO> findByCategory(UUID categoryId);

<<<<<<< HEAD
    List<PropertyDTO> findAllPropertiesByHostId(UUID hostId);

//	List<Property> findByCategory(UUID categoryId);

	
	List<PropertyDTO> findByPropertyNameOrAddressContainingIgnoreCase(String keySearch);
=======
>>>>>>> origin/namhh-refresh-token
}
