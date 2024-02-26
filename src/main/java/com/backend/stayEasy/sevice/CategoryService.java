package com.backend.stayEasy.sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.convertor.CategoryConverter;
import com.backend.stayEasy.dto.CategoryDTO;
import com.backend.stayEasy.entity.Category;
import com.backend.stayEasy.repository.ICategoryRepository;

@Service
public class CategoryService implements ICategoryService{
	
	@Autowired
	private ICategoryRepository categoryRepository;
	
	@Autowired
	private CategoryConverter categoryConverter;

	@Override
	public List<CategoryDTO> findAll() {
		// TODO Auto-generated method stub	
		List<Category> categoryList = categoryRepository.findAll();
		return categoryConverter.arrayToDTO(categoryList);
	}

	

}
