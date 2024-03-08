package com.backend.stayEasy.convertor;

import com.backend.stayEasy.dto.CategoryDTO;
import com.backend.stayEasy.entity.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.CategoryDTO;
import com.backend.stayEasy.dto.ImagesDTO;
import com.backend.stayEasy.entity.Category;
import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.entity.Property;


@Component
public class CategoryConverter {
	public CategoryDTO toDTO(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setCategoryId(category.getCategoryId());
		categoryDTO.setCategoryName(category.getCategoryName());
		
//		Set<Property> properties = category.getProperties();
//		if (!properties.isEmpty()) {
//			List<UUID> propertyIds = properties.stream()
//					.map(Property::getPropertyId)
//					.collect(Collectors.toList());
//			
//			categoryDTO.setPropertyId(propertyIds);
//		}
		
		return categoryDTO;
	}

	public List<CategoryDTO> arrayToDTO(List<Category> categoryList) {
		List<CategoryDTO> categoryDTOList = new ArrayList<>();
		for (Category category : categoryList) {
			categoryDTOList.add(toDTO(category));
		}
		return categoryDTOList;
	}
	
}
