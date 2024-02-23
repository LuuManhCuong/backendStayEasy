package com.backend.stayEasy.convertor;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.BookingDetail;
import com.backend.stayEasy.entity.Feedback;
import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.entity.Like;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.entity.PropertyCategory;
import com.backend.stayEasy.entity.PropertyUilitis;
import com.backend.stayEasy.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Component
public class PropertyConverter {
	
	@Autowired
	private UserConverter userConverter;
	
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
		propertyDTO.setOwner(userConverter.toDTO(property.getUser()));
		return propertyDTO;
	}
}
