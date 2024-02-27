package com.backend.stayEasy.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.convertor.PropertyConverter;
import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.repository.IPropertyRepository;
import com.backend.stayEasy.sevice.IPropertyService;


@RestController
@CrossOrigin
@RequestMapping(value="/api/v1/stayeasy/property", produces = "application/json")
public class PropertyAPI {
	

	@Autowired
	private IPropertyService propertyService;
	
	@Autowired
	private IPropertyRepository propertyRepository;
	@Autowired
	private PropertyConverter propertyConverter;
	
	@GetMapping
	public List<PropertyDTO> getProperty(){
		return propertyService.findAll();
	}
	
	@GetMapping("/full")
	public List<PropertyDTO> getFullProperty(){
		List<Property> properties =  propertyRepository.findAllPropertiesWithSets();
		System.out.println("oki");
		List<PropertyDTO> propertyDTOs = new ArrayList<>();
		for (Property property : properties) {
			System.out.println("element: " + property);
			propertyDTOs.add(propertyConverter.toDTO(property));
		}
		return propertyDTOs;
	}
	
	@GetMapping("/{id}")
	public PropertyDTO getDetailProperty(@PathVariable("id") UUID id) {
		return propertyService.findById(id);
	}
	
	@GetMapping("/category/{category}")
	public List<PropertyDTO> getPropertyByCategory(@PathVariable("category") UUID categoryId){
		return propertyService.findByCategory(categoryId);
	}
	
}
