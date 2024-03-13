package com.backend.stayEasy.sevice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import com.backend.stayEasy.dto.CategoryDTO;
import com.backend.stayEasy.dto.ImagesDTO;
import com.backend.stayEasy.dto.LikeRequestDTO;
import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.dto.PropertyUtilitiesDTO;
import com.backend.stayEasy.dto.RulesDTO;
import com.backend.stayEasy.entity.Category;
import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.entity.Like;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.entity.PropertyCategory;
import com.backend.stayEasy.entity.PropertyRules;
import com.backend.stayEasy.entity.PropertyUilitis;
import com.backend.stayEasy.entity.Rules;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.repository.CategoryRepository;
import com.backend.stayEasy.repository.IImageRepository;
import com.backend.stayEasy.repository.IPropertyCategoryRepository;
import com.backend.stayEasy.repository.IPropertyRepository;
import com.backend.stayEasy.repository.LikeRepository;
import com.backend.stayEasy.repository.PropertyUilitisRepository;
import com.backend.stayEasy.repository.RulesRepository;
import com.backend.stayEasy.sevice.impl.IPropertyService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PropertyService implements IPropertyService {

	@Autowired
	private IPropertyRepository propertyRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private PropertyConverter propertyConverter;

	@Autowired
	private IImageRepository imageRepository;

	@Autowired
	private IPropertyCategoryRepository propertyCategoryRepository;

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private LikeConverter likeConverter;
	
	@Autowired 
	private RulesRepository rulesRepository;
	
	
	@Override
	public List<PropertyDTO> findAll() {

		List<PropertyDTO> result = new ArrayList<>();

		for (Property p : propertyRepository.findAll()) {
			List<Like> likes = likeRepository.findByPropertyPropertyId(p.getPropertyId());
			Set<LikeRequestDTO> likeRequestDTOs = likeConverter.arraytoDTO(likes);
			PropertyDTO temp = propertyConverter.toDTO(p);
			temp.setLikeList(likeRequestDTOs);
			result.add(temp);
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
		List<PropertyRules> propertyRules = new ArrayList<>();

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
		
		for (RulesDTO rulesDTO : propertyDTO.getRulesList()) {
			PropertyRules temp = new PropertyRules();
			temp.setProperty(property);
			Optional<Rules> rulesOp = rulesRepository.findById(rulesDTO.getRulesId());
			if (rulesOp.isPresent()) { // Kiểm tra xem giá trị tồn tại trong Optional hay không
				Rules rules = rulesOp.get(); // Trích xuất giá trị User từ Optional
			    temp.setRules(rules); // Gán giá trị User cho property
			}
			propertyRules.add(temp);
		}

		property.setImages(images);
		property.setPropertyCategories(propertyCategory);
		property.setPropertyRules(propertyRules);

		propertyRepository.save(property);

		return propertyConverter.toDTO(property);
	}

	@Override
	public PropertyDTO update(UUID propertyId, PropertyDTO updatePropertyDTO) {
		 Optional<Property> propertyOptional = propertyRepository.findById(propertyId);
		 
		    if (propertyOptional.isPresent()) {
		        Property existingProperty = propertyOptional.get();
		        Property updatedProperty = propertyConverter.toEntity(updatePropertyDTO);
		        
		        // Copy các thông tin từ updatedProperty vào existingProperty
		        existingProperty.setPropertyName(updatedProperty.getPropertyName());
		        existingProperty.setDescription(updatedProperty.getDescription());
		        existingProperty.setThumbnail(updatedProperty.getThumbnail());
		        existingProperty.setPrice(updatedProperty.getPrice());
		        existingProperty.setNumGuests(updatedProperty.getNumGuests());
		        existingProperty.setDiscount(updatedProperty.getDiscount());
		        		        
		        List<PropertyCategory> updatedPropertyCategories = new ArrayList<>();

		     // Lưu category cần update
		     for (CategoryDTO categoryDTO : updatePropertyDTO.getCategories()) {
		         PropertyCategory temp = new PropertyCategory();
		         temp.setProperty(existingProperty);
		         Optional<Category> categoryOp = categoryRepository.findById(categoryDTO.getCategoryId());
		         if (categoryOp.isPresent()) { 
		             Category category = categoryOp.get(); 
		             temp.setCategory(category); 
		         }
		         updatedPropertyCategories.add(temp);
		     }

		     // Check category thừa
		     Iterator<PropertyCategory> iterator = existingProperty.getPropertyCategories().iterator();
		     while (iterator.hasNext()) {
		         PropertyCategory propertyCategory = iterator.next();
		         boolean categoryFound = false;
		         for (PropertyCategory updatedCategory : updatedPropertyCategories) {
		             if (propertyCategory.getCategory().getCategoryId() == updatedCategory.getCategory().getCategoryId()) {
		                 categoryFound = true;
		                 break;
		             }
		         }
		         if (!categoryFound) {
		             iterator.remove();
		         }
		     }

		     // Check category mới add vô
		     for (PropertyCategory updatedCategory : updatedPropertyCategories) {
		    	    boolean categoryExists = false;
		    	    for (PropertyCategory existingCategory : existingProperty.getPropertyCategories()) {
		    	        if (existingCategory.getCategory().getCategoryId().equals(updatedCategory.getCategory().getCategoryId())) {
		    	            categoryExists = true;
		    	            break;
		    	        }
		    	    }
		    	    if (!categoryExists) {
		    	        existingProperty.getPropertyCategories().add(updatedCategory);
		    	    }
		    	}
		        
		     	// xoá image không update
		        List<Images> imagesToRemove = new ArrayList<>();
		        for (Images existingImage : existingProperty.getImages()) {
		            boolean existsInUpdate = false;
		            for (ImagesDTO imagesDTO : updatePropertyDTO.getImagesList()) {
		                if (existingImage.getUrl().equals(imagesDTO.getUrl())) {
		                    existsInUpdate = true;
		                    break;
		                }
		            }
		            if (!existsInUpdate) {
		                // Xóa ảnh từ cơ sở dữ liệu
		                imageRepository.delete(existingImage);
		                imagesToRemove.add(existingImage);
		            }
		        }
		        existingProperty.getImages().removeAll(imagesToRemove);

		        // Check đã có images chưa
		        for (ImagesDTO imagesDTO : updatePropertyDTO.getImagesList()) {
		            boolean exists = false;
		            for (Images existingImage : existingProperty.getImages()) {
		                if (existingImage.getUrl().equals(imagesDTO.getUrl())) {
		                    exists = true;
		                    break;
		                }
		            }
		            // chưa có add vô
		            if (!exists) {
		                Images newImage = new Images(imagesDTO.getUrl(), imagesDTO.getDescription(), existingProperty);
		                existingProperty.getImages().add(newImage);
		            }
		        }
		        
		        List<PropertyRules> updatedPropertyRules = new ArrayList<>();
		        
		        for (RulesDTO rulesDTO : updatePropertyDTO.getRulesList()) {
			         PropertyRules temp = new PropertyRules();
			         temp.setProperty(existingProperty);
			         Optional<Rules> rulesOp = rulesRepository.findById(rulesDTO.getRulesId());
			         if (rulesOp.isPresent()) { 
			             Rules rules = rulesOp.get(); 
			             temp.setRules(rules); 
			         }
			         updatedPropertyRules.add(temp);
			     }
		        

			     // Check rules thừa
			     Iterator<PropertyRules> iteratorRules = existingProperty.getPropertyRules().iterator();
			     while (iteratorRules.hasNext()) {
			    	 PropertyRules propertyRules = iteratorRules.next();
			         boolean rulesFound = false;
			         for (PropertyRules updatedRules : updatedPropertyRules) {
			             if (propertyRules.getRules().getRulesId() == updatedRules.getRules().getRulesId()) {
			            	 rulesFound = true;
			                 break;
			             }
			         }
			         if (!rulesFound) {
			        	 iteratorRules.remove();
			         }
			     }

			     // Check rules mới add vô
			     for (PropertyRules updatedRules : updatedPropertyRules) {
			    	    boolean rulesExists = false;
			    	    for (PropertyRules existingRules : existingProperty.getPropertyRules()) {
			    	        if (existingRules.getRules().getRulesId().equals(updatedRules.getRules().getRulesId())) {
			    	        	rulesExists = true;
			    	            break;
			    	        }
			    	    }
			    	    if (!rulesExists) {
			    	        existingProperty.getPropertyRules().add(updatedRules);
			    	    }
			    	}
		        
		        
		        Property result = propertyRepository.save(existingProperty);

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
	@Override
	public List<PropertyDTO> findByCategory(UUID categoryId) {
		List<PropertyDTO> result = new ArrayList<>();
		List<PropertyCategory> propertyCategory = propertyCategoryRepository.findByCategoryCategoryId(categoryId);

		for (PropertyCategory p : propertyCategory) {
			Property temp = propertyRepository.findByPropertyId(p.getProperty().getPropertyId());
			List<Like> likes = likeRepository.findByPropertyPropertyId(temp.getPropertyId());
			Set<LikeRequestDTO> likeRequestDTOs = likeConverter.arraytoDTO(likes);
			PropertyDTO temp2 = propertyConverter.toDTO(temp);
			temp2.setLikeList(likeRequestDTOs);
			result.add(temp2);
		}
		return result;
	}
	public List<PropertyDTO> findAllPropertiesByHostId(UUID hostId) {
		// Truy vấn cơ sở dữ liệu để lấy danh sách các property có userId giống với hostId
		List<Property> properties = propertyRepository.findAllByUserId(hostId);

		// Chuyển đổi danh sách các property sang danh sách PropertyDTO
		return properties.stream()
				.map(propertyConverter::toDTO)
				.collect(Collectors.toList());
	}
	
	
	@Override
	public List<PropertyDTO> findByPropertyNameOrAddressContainingIgnoreCase(String keySearch) {
		List<Property> propertyList = propertyRepository.findByPropertyNameOrAddressContainingIgnoreCase(keySearch);
		return propertyConverter.arrayToDTO(propertyList);
	}

}