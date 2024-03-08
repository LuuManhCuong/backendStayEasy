package com.backend.stayEasy.sevice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.convertor.CategoryConverter;
import com.backend.stayEasy.convertor.FeedbackConverter;
import com.backend.stayEasy.convertor.ImagesConventer;
import com.backend.stayEasy.convertor.LikeConverter;
import com.backend.stayEasy.convertor.PropertyConverter;
import com.backend.stayEasy.convertor.PropertyUtilitiesConverter;
import com.backend.stayEasy.dto.CategoryDTO;
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
import com.backend.stayEasy.entity.User;
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
	private CategoryConverter categoryConverter;

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

		List<PropertyDTO> result = new ArrayList<>();

		for (Property p : propertyRepository.findAll()) {
			result.add(propertyConverter.toDTO(p));
		}

		return result;
	}

	@Override
	public PropertyDTO findById(UUID id) {
		Property property = propertyRepository.findByPropertyId(id);
		return propertyConverter.toDTO(property);
	}

	@Override
	public PropertyDTO add(PropertyDTO propertyDTO) {
		Property property = new Property();
		List<Images> images = new ArrayList<>();
		List<PropertyCategory> propertyCategory = new ArrayList<>();

		property = propertyConverter.toEntity(propertyDTO);

		for (ImagesDTO i : propertyDTO.getImagesList()) {
			images.add(new Images(i.getUrl(), i.getDescription(), property));
		}
		
		for (CategoryDTO categoryDTO : propertyDTO.getCategories()) {
			PropertyCategory temp = new PropertyCategory();
			temp.setProperty(property);
			Optional<Category> categoryOp = categoryRepository.findById(categoryDTO.getCategoryId());
			if (categoryOp.isPresent()) { // Kiểm tra xem giá trị tồn tại trong Optional hay không
				Category category = categoryOp.get(); // Trích xuất giá trị User từ Optional
			    temp.setCategory(category); // Gán giá trị User cho property
			}
			propertyCategory.add(temp);
		}


		property.setImages(images);
		property.setPropertyCategories(propertyCategory);

		propertyRepository.save(property);

		return propertyConverter.toDTO(property);
	}

	@Override
	public PropertyDTO update(UUID propertyId, PropertyDTO updatePropertyDTO) {

		Optional<Property> property = propertyRepository.findById(propertyId);
		
		List<Images> images = new ArrayList<>();
		List<PropertyCategory> propertyCategory = new ArrayList<>();

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
			for (CategoryDTO categoryDTO : updatePropertyDTO.getCategories()) {
				PropertyCategory temp = new PropertyCategory();
				temp.setProperty(editProperty);
				Optional<Category> categoryOp = categoryRepository.findById(categoryDTO.getCategoryId());
				if (categoryOp.isPresent()) { // Kiểm tra xem giá trị tồn tại trong Optional hay không
					Category category = categoryOp.get(); // Trích xuất giá trị User từ Optional
				    temp.setCategory(category); // Gán giá trị User cho property
				}
				propertyCategory.add(temp);
			}
			
			editProperty.setImages(images);
			
			editProperty.setPropertyCategories(propertyCategory);
			
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
