package com.backend.stayEasy.api;

<<<<<<< HEAD
import com.backend.stayEasy.dto.CategoryDTO;
import com.backend.stayEasy.sevice.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
=======
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.dto.CategoryDTO;
import com.backend.stayEasy.sevice.impl.ICategoryService;
>>>>>>> origin/namhh-update-infor

@RestController
@CrossOrigin
@RequestMapping(value="/api/v1/stayeasy/category", produces = "application/json")
public class CategoryAPI {
	
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping("")
	public List<CategoryDTO> getCategory(){
		return categoryService.findAll();
	}
	
}
