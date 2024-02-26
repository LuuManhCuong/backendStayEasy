package com.backend.stayEasy.sevice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.convertor.FeedbackConverter;
import com.backend.stayEasy.convertor.ImagesConventer;
import com.backend.stayEasy.convertor.PropertyConverter;
import com.backend.stayEasy.convertor.PropertyUtilitiesConverter;
import com.backend.stayEasy.dto.ImagesDTO;
import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.dto.PropertyUtilitiesDTO;
import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.entity.PropertyCategory;
import com.backend.stayEasy.entity.PropertyUilitis;
import com.backend.stayEasy.repository.IImageRepository;
import com.backend.stayEasy.repository.IPropertyCategoryRepository;
import com.backend.stayEasy.repository.IPropertyRepository;
import com.backend.stayEasy.repository.PropertyUilitisRepository;


@Service
public class PropertyService implements IPropertyService{

	@Autowired
	private IPropertyRepository propertyRepository;
	
	@Autowired 
	private IPropertyCategoryRepository propertyCategoryRepository;
	
	@Autowired
	private PropertyConverter propertyConverter;
	
	@Autowired
	private ImagesConventer imagesConverter;
	
	@Autowired
	private FeedbackConverter feedbackConverter;
	
	@Autowired
	private PropertyUtilitiesConverter propertyUtilitiesConverter;
	
	@Autowired
	private IImageRepository imageRepository;
	
	@Autowired
	private PropertyUilitisRepository propertyUtilitiesRepository;
	
	@Override
	public List<PropertyDTO> findAll() {
		// TODO Auto-generated method stub
		return propertyConverter.arrayToDTO(propertyRepository.findAll());
	}

	@Override
	public PropertyDTO findById(UUID id) {
		// TODO Auto-generated method stub
		Property property = propertyRepository.findById(id).get();
		List<PropertyUilitis> propertyUtilitiesList = propertyUtilitiesRepository.findByPropertyPropertyId(id);
		List<Images> imagesList = imageRepository.findByPropertyPropertyId(id);
		PropertyDTO propertyDTO = propertyConverter.toDTO(property);
		Set<ImagesDTO> imagesSet = new HashSet<>(imagesConverter.arrayToDTO(imagesList));
		Set<PropertyUtilitiesDTO> propertyUtilitiesSet = new HashSet<>(propertyUtilitiesConverter.arrayToDTO(propertyUtilitiesList));
		propertyDTO.setImagesList(imagesSet);
		propertyDTO.setPropertyUtilitis(propertyUtilitiesSet);
		return propertyDTO;
	}

	@Override
	public List<PropertyDTO> findByCategory(UUID categoryId) {
		// TODO Auto-generated method stub
		List<PropertyCategory> propertyCategoryList  = propertyCategoryRepository.findByCategoryCategoryId(categoryId);
		List<PropertyDTO> properties = new ArrayList<>();
		for (PropertyCategory propertyCategory : propertyCategoryList) {
			properties.add(propertyConverter.toDTO(propertyCategory.getProperty()));
		}
	    return properties;
	}

}
