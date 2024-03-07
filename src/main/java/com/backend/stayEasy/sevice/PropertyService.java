package com.backend.stayEasy.sevice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
import com.backend.stayEasy.entity.Category;
import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.entity.Like;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.entity.PropertyCategory;
import com.backend.stayEasy.entity.PropertyUilitis;
import com.backend.stayEasy.repository.ICategoryRepository;
import com.backend.stayEasy.repository.IImageRepository;
import com.backend.stayEasy.repository.IPropertyCategoryRepository;
import com.backend.stayEasy.repository.IPropertyRepository;
import com.backend.stayEasy.repository.LikeRepository;
import com.backend.stayEasy.repository.PropertyUilitisRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PropertyService implements IPropertyService {

	@Autowired
	private IPropertyRepository propertyRepository;

	@Autowired
	private ICategoryRepository categoryRepository;

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
			 Set<Images> images = imageRepository.findByPropertyPropertyId(property.getPropertyId());
			 
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
		Property property = propertyRepository.findByPropertyId(id);
		return propertyConverter.toDTO(property);
	}

	@Override
	public PropertyDTO add(PropertyDTO propertyDTO) {
		Property property = new Property();
		Set<Images> images = new HashSet<>();
		Set<Category> categories = new HashSet<>();

		property = propertyConverter.toEntity(propertyDTO);

		for (ImagesDTO i : propertyDTO.getImagesList()) {
			images.add(new Images(i.getUrl(), i.getDescription(), property));
		}

		// Convert CategoryDTO to Category and associate with the property
		if (propertyDTO.getCategoryIds() != null && !propertyDTO.getCategoryIds().isEmpty()) {
			for (UUID categoryId : propertyDTO.getCategoryIds()) {
				Category category = categoryRepository.findById(categoryId).orElse(null);
				if (category != null) {
					categories.add(category);
				}
			}
		}

		property.setImages(images);
		property.setCategories(categories);

		propertyRepository.save(property);

		return propertyConverter.toDTO(property);
	}

	@Override
	public PropertyDTO update(UUID propertyId, PropertyDTO updatePropertyDTO) {

		Optional<Property> property = propertyRepository.findById(propertyId);
		
		Set<Images> images = new HashSet<>();
		Set<Category> categories = new HashSet<>();

		if (property.isPresent()) {
			Property editProperty = property.get();
			
			editProperty = propertyConverter.toEntity(updatePropertyDTO);

			editProperty.setPropertyName(updatePropertyDTO.getPropertyName());
			editProperty.setDescription(updatePropertyDTO.getDescription());
			editProperty.setThumbnail(updatePropertyDTO.getThumbnail());
			editProperty.setPrice(updatePropertyDTO.getPrice());
			editProperty.setNumGuests(updatePropertyDTO.getNumGuests());
			editProperty.setDiscount(updatePropertyDTO.getDiscount());
			
			for (ImagesDTO i : updatePropertyDTO.getImagesList()) {
				images.add(new Images(i.getUrl(), i.getDescription(), editProperty));
			}
			
			// Convert CategoryDTO to Category and associate with the property
			if (updatePropertyDTO.getCategoryIds() != null && !updatePropertyDTO.getCategoryIds().isEmpty()) {
				for (UUID categoryId : updatePropertyDTO.getCategoryIds()) {
					Category category = categoryRepository.findById(categoryId).orElse(null);
					if (category != null) {
						categories.add(category);
					}
				}
			}
			
			editProperty.setImages(images);
			
			editProperty.setCategories(categories);
			
			Property result = propertyRepository.save(editProperty);
			

			return propertyConverter.toDTO(result);

		} else {
			throw new EntityNotFoundException("Property not found!");
		}

	}

	@Override
	public Property delete(UUID propertyId) {

		if (propertyRepository.existsById(propertyId)) {
			propertyRepository.deleteById(propertyId);
		} else {
			throw new EntityNotFoundException("Property not found with ID: " + propertyId);
		}

		return null;
	}

//	@Override
//	public List<Property> findByCategory(UUID categoryId) {
//		
//		List<PropertyCategory> propertyCategories = propertyCategoryRepository.findByCategoryCategoryId(categoryId);
//		List<Property> properties = new ArrayList<>();
//		for (PropertyCategory p : propertyCategories) {
//			properties.add(p.getProperty());
//		}
//		
//		return properties;
//	}

}
