package com.backend.stayEasy.convertor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;


import com.backend.stayEasy.dto.PropertyUtilitiesDTO;
import com.backend.stayEasy.entity.PropertyUilitis;



@Component
public class PropertyUtilitiesConverter {
	public PropertyUtilitiesDTO toDTO(PropertyUilitis propertyUtiliti) {
		PropertyUtilitiesDTO propertyUtilitiesDTO = new PropertyUtilitiesDTO();
		propertyUtilitiesDTO.setPropertyUtilitiesId(propertyUtiliti.getPropertyUtilitiesId());
		propertyUtilitiesDTO.setQuantity(propertyUtiliti.getQuantity());
		propertyUtilitiesDTO.setUtilitiesName(propertyUtiliti.getUtilities().getUtilitiesName());
		return propertyUtilitiesDTO;
	}
	
	public List<PropertyUtilitiesDTO> arrayToDTO(List<PropertyUilitis> propertyUtilitiesList) {
		List<PropertyUtilitiesDTO> propertyUtilitiesDTOList = new ArrayList<>();
		for (PropertyUilitis propertyUtiliti : propertyUtilitiesList) {
			propertyUtilitiesDTOList.add(toDTO(propertyUtiliti));
		}
		return propertyUtilitiesDTOList;
	}
}
