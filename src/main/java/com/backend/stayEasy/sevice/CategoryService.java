package com.backend.stayEasy.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.entity.Category;
import com.backend.stayEasy.repository.ICategoryRepository;

@Service
public class CategoryService implements ICategoryService{
	
	@Autowired
	private ICategoryRepository categoryRepository;

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	

}
