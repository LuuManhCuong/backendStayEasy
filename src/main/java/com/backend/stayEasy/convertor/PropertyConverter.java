package com.backend.stayEasy.convertor;

import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.Property;

@Component
public class PropertyConverter {
	public PropertyDTO toDTO(Property property) {
		PropertyDTO propertyDTO = new PropertyDTO();
		propertyDTO.setAddress(property.getAddress());
		propertyDTO.setDescription(property.getDescription());
		propertyDTO.setDiscount(property.getDiscount());
		propertyDTO.setNull(property.isNull());
		propertyDTO.setNumGuests(property.getNumGuests());
		propertyDTO.setPrice(property.getPrice());
		propertyDTO.setPropertyId(property.getPropertyId());
		propertyDTO.setPropertyName(property.getPropertyName());
		propertyDTO.setRating(property.getRating());
		propertyDTO.setThumbnail(property.getThumbnail());
		propertyDTO.setFeedbackList(property.getFeedbacks());
		propertyDTO.setImagesList(property.getImages());
		propertyDTO.setPropertyUtilitis(property.getPropertyUilitis());
		propertyDTO.setOwner(property.getUser());
		return propertyDTO;
	}
}
