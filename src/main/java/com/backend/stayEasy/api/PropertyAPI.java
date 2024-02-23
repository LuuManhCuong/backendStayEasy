package com.backend.stayEasy.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.sevice.IPropertyService;


@RestController
@CrossOrigin
@RequestMapping(value="/api/v1/stayeasy/property", produces = "application/json")
public class PropertyAPI {
	

	@Autowired
	private IPropertyService propertyService;
	
	@GetMapping("")
	public List<Property> getProperty(){
		return propertyService.findAll();
	}
	
	@GetMapping("/{id}")
	public PropertyDTO getDetailProperty(@PathVariable("id") UUID id) {
		return propertyService.findById(id);
	}
	
	@GetMapping("/category/{category}")
	public List<Property> getPropertyByCategory(@PathVariable("category") UUID categoryId){
		return propertyService.findByCategory(categoryId);
	}
	
}
