package com.backend.stayEasy.sevice;

import java.util.List;

import com.backend.stayEasy.dto.CategoryDTO;



public interface ICategoryService {
	List<CategoryDTO> findAll();
}
