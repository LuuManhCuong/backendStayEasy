package com.backend.stayEasy.sevice;

import java.util.List;

import com.backend.stayEasy.entity.Category;

public interface ICategoryService {
	List<Category> findAll();
}
