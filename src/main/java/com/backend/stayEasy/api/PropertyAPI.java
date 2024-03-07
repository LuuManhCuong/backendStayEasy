package com.backend.stayEasy.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.sevice.IPropertyService;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping(value = "/api/v1/stayeasy/property", produces = "application/json")
public class PropertyAPI {

	@Autowired
	private IPropertyService propertyService;

	@GetMapping
	public List<PropertyDTO> getAllProperty() {
		return propertyService.findAll();
	}

	@GetMapping("/{id}")
	public PropertyDTO getPropertyById(@PathVariable("id") UUID id) {
		return propertyService.findById(id);
	}

	@PostMapping("/add")
	public PropertyDTO addProperty(@Validated @RequestBody PropertyDTO propertyDTO) {
		return propertyService.add(propertyDTO);
	}

	@PutMapping("/edit/{id}")
	public PropertyDTO editProperty(@PathVariable("id") UUID propertyId,
			@Validated @RequestBody PropertyDTO updaPropertyDTO) {
		return propertyService.update(propertyId, updaPropertyDTO);
	}
	
	@DeleteMapping("/delete/{id}")
	public Property delete(@PathVariable("id") UUID propertyId) {
		return propertyService.delete(propertyId);
	}
	
//	@GetMapping("/category/{category}")
//	public List<Property> getPropertyByCategory(@PathVariable("category") UUID categoryId) {
//		return propertyService.findByCategory(categoryId);
//	}

}
