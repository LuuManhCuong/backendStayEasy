package com.backend.stayEasy.convertor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.CategoryDTO;
import com.backend.stayEasy.entity.Category;


@Component
public class CategoryConverter {
	public CategoryDTO toDTO(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setCategoryId(category.getCategoryId());
		categoryDTO.setCategoryName(category.getCategoryName());
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
