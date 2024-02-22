package com.backend.stayEasy.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.entity.Category;
import com.backend.stayEasy.sevice.ICategoryService;

@RestController
@CrossOrigin
@RequestMapping(value="/api/category", produces = "application/json")
public class CategoryAPI {
	
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping("")
	public List<Category> getCategory(){
		return categoryService.findAll();
	}
	
}
