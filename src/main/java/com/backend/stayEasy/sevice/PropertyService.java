package com.backend.stayEasy.sevice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.convertor.FeedbackConverter;
import com.backend.stayEasy.convertor.ImagesConventer;
import com.backend.stayEasy.convertor.LikeConverter;
import com.backend.stayEasy.convertor.PropertyConverter;
import com.backend.stayEasy.convertor.PropertyUtilitiesConverter;
import com.backend.stayEasy.dto.ImagesDTO;
import com.backend.stayEasy.dto.LikeRequestDTO;
import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.dto.PropertyUtilitiesDTO;
import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.entity.Like;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.entity.PropertyCategory;
import com.backend.stayEasy.entity.PropertyUilitis;
import com.backend.stayEasy.repository.IImageRepository;
import com.backend.stayEasy.repository.IPropertyCategoryRepository;
import com.backend.stayEasy.repository.IPropertyRepository;
import com.backend.stayEasy.repository.LikeRepository;
import com.backend.stayEasy.repository.PropertyUilitisRepository;
import com.google.common.base.Optional;

import jakarta.transaction.Transactional;


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
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private LikeConverter likeConverter;
	
	@Override
	public List<PropertyDTO> findAll() {
		 List<Property> properties = propertyRepository.findAll(); // Lấy tất cả các Property
		 List<PropertyDTO> propertyDTOs = new ArrayList<>(); // Danh sách PropertyDTO để lưu kết quả
		 
		 for (Property property : properties) {
			 PropertyDTO propertyDTO = propertyConverter.toDTO(property); // Chuyển đổi Property thành PropertyDTO
			 List<Like> likes = likeRepository.findByPropertyPropertyId(property.getPropertyId());
			 List<Images> images = imageRepository.findByPropertyPropertyId(property.getPropertyId());
			 
			 Set<ImagesDTO> imagesDTO = imagesConverter.arrayToDTO(images);
			 Set<LikeRequestDTO> likeRequestDTOs = likeConverter.arraytoDTO(likes);
			 propertyDTO.setLikeList(likeRequestDTOs);
			 propertyDTO.setImagesList(imagesDTO);
			 propertyDTOs.add(propertyDTO);
		}
		
		return propertyDTOs;

		
	}

	@Override
	public PropertyDTO findById(UUID id) {
		// TODO Auto-generated method stub
		Property property = propertyRepository.findById(id).get();
		List<PropertyUilitis> propertyUtilitiesList = propertyUtilitiesRepository.findByPropertyPropertyId(id);
		List<Images> imagesList = imageRepository.findByPropertyPropertyId(id);
		PropertyDTO propertyDTO = propertyConverter.toDTO(property);
		Set<PropertyUtilitiesDTO> propertyUtilitiesSet = new HashSet<>(propertyUtilitiesConverter.arrayToDTO(propertyUtilitiesList));
		propertyDTO.setImagesList(imagesConverter.arrayToDTO(imagesList));
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

	@Override
	public List<PropertyDTO> findByUserId(UUID userId) {
		// TODO Auto-generated method stub
		return propertyConverter.arrayToDTO(propertyRepository.findByUserId(userId));
	}

	@Override
	public Property deleteProperty(UUID id) {
		if(propertyRepository.existsById(id)) {
			Property test = propertyRepository.findByPropertyId(id);
			propertyRepository.delete(test);;
		}else {
			System.out.println("loi");
		}
		return null;
	}
	

	
	
}