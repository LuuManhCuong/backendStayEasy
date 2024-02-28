package com.backend.stayEasy.sevice;

import com.backend.stayEasy.convertor.PropertyConverter;
import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.entity.PropertyCategory;
import com.backend.stayEasy.repository.IImageRepository;
import com.backend.stayEasy.repository.IPropertyCategoryRepository;
import com.backend.stayEasy.repository.IPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PropertyService implements IPropertyService{

	@Autowired
	private IPropertyRepository propertyRepository;
	
	@Autowired 
	private IPropertyCategoryRepository propertyCategoryRepository;
	
	@Autowired
	private PropertyConverter propertyConverter;
	
	@Autowired
	private IImageRepository imageRepository;
	
	@Override
	public List<Property> findAll() {
		// TODO Auto-generated method stub
		return propertyRepository.findAll();
	}
	public Property getById(UUID id)  {
		Property property;
		property = propertyRepository.findById(id).get();
		return property;
	}
	@Override
	public PropertyDTO findById(UUID id) {
		// TODO Auto-generated method stub
		Property property = propertyRepository.findById(id).get();
		List<Images> imagelist = imageRepository.findByPropertyPropertyId(id);
		PropertyDTO propertyDTO = propertyConverter.toDTO(property);
		Set<Images> imagesSet = new HashSet<>(imagelist);
		propertyDTO.setImagesList(imagesSet);
		return propertyDTO;
	}

	@Override
	public List<Property> findByCategory(UUID categoryId) {
		// TODO Auto-generated method stub
		List<PropertyCategory> propertyCategoryList  = propertyCategoryRepository.findByCategoryCategoryId(categoryId);
		List<Property> properties = new ArrayList<>();
		for (PropertyCategory propertyCategory : propertyCategoryList) {
			properties.add(propertyCategory.getProperty());
		}
	    return properties;
	}

}
