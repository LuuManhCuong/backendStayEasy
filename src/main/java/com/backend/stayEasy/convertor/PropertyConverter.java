package com.backend.stayEasy.convertor;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.CategoryDTO;
import com.backend.stayEasy.dto.ImagesDTO;
import com.backend.stayEasy.dto.LikeRequestDTO;
import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.entity.PropertyCategory;
import com.backend.stayEasy.entity.User;
=======
import com.backend.stayEasy.dto.ImagesDTO;
import com.backend.stayEasy.dto.LikeRequestDTO;
import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.Category;
import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.repository.ICategoryRepository;
>>>>>>> origin/loc-check-booking
import com.backend.stayEasy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

<<<<<<< HEAD
=======
import java.util.*;

>>>>>>> origin/loc-check-booking

@Component
public class PropertyConverter {

	@Autowired
	private ImagesConventer imagesConventer;

	@Autowired
	private CategoryConverter categoryConverter;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private UserRepository userRepository;
	

	public PropertyDTO toDTO(Property property) {
		List<ImagesDTO> listImages = new ArrayList<>();
		List<CategoryDTO> listCategory = new ArrayList<>();
		PropertyDTO propertyDTO = new PropertyDTO();
		propertyDTO.setAddress(property.getAddress());
		propertyDTO.setDescription(property.getDescription());
		propertyDTO.setDiscount(property.getDiscount());
//		propertyDTO.setNull(property.isNull());
		propertyDTO.setNumGuests(property.getNumGuests());
		propertyDTO.setPrice(property.getPrice());
		propertyDTO.setPropertyId(property.getPropertyId());
		propertyDTO.setPropertyName(property.getPropertyName());
//		propertyDTO.setRating(property.getRating());
		if (!property.getPropertyCategories().isEmpty()) {
			for (PropertyCategory c : property.getPropertyCategories()) {
				listCategory.add(categoryConverter.toDTO(c.getCategory()));
			}
		}
		propertyDTO.setCategories(listCategory);
		propertyDTO.setThumbnail(property.getThumbnail());
		if (!property.getImages().isEmpty()) {
			for (Images i : property.getImages()) {
				listImages.add(imagesConventer.toDTO(i));
			}
		}
		propertyDTO.setImagesList(listImages);
//		propertyDTO.setOwnerId(property.getUser().getId());

		if (property.getUser() != null) {
			propertyDTO.setOwner(userConverter.toDTO(property.getUser()));
		}
		return propertyDTO;
	}

	public Property toEntity(PropertyDTO propertyDTO) {
		Property property = new Property();
		if(propertyDTO.getPropertyId()!=null) {
			property.setPropertyId(propertyDTO.getPropertyId());
		}
		property.setAddress(propertyDTO.getAddress());
		property.setDescription(propertyDTO.getDescription());
		property.setDiscount(propertyDTO.getDiscount());
		property.setNull(false);
		property.setNumGuests(propertyDTO.getNumGuests());
		property.setPrice(propertyDTO.getPrice());
		property.setPropertyId(propertyDTO.getPropertyId());
		property.setPropertyName(propertyDTO.getPropertyName());
//		property.setRating(5.0);
		property.setThumbnail(propertyDTO.getThumbnail());
		
		Optional<User> optionalUser = userRepository.findById(propertyDTO.getOwner().getId());
				if (optionalUser.isPresent()) { // Kiểm tra xem giá trị tồn tại trong Optional hay không
				    User user = optionalUser.get(); // Trích xuất giá trị User từ Optional
				    property.setUser(user); // Gán giá trị User cho property
				} else {
				   property.setUser(null);
				}
		return property;

	}

//	no

	public PropertyDTO toDTO(Property property, Set<LikeRequestDTO> likeRequestDTOlist) {
		PropertyDTO propertyDTO = new PropertyDTO();
		propertyDTO.setAddress(property.getAddress());
		propertyDTO.setDescription(property.getDescription());
		propertyDTO.setDiscount(property.getDiscount());
		propertyDTO.setNull(property.isNull());
		propertyDTO.setNumGuests(property.getNumGuests());
		propertyDTO.setPrice(property.getPrice());
		propertyDTO.setPropertyId(property.getPropertyId());
		propertyDTO.setPropertyName(property.getPropertyName());
		propertyDTO.setRating(property.getRating());
		propertyDTO.setThumbnail(property.getThumbnail());
		propertyDTO.setOwner(userConverter.toDTO(property.getUser()));
		propertyDTO.setLikeList(likeRequestDTOlist);
		return propertyDTO;
	}

	public List<PropertyDTO> arrayToDTO(List<Property> propertyList, List<LikeRequestDTO> likeRequestDTOlist) {
		List<PropertyDTO> propertyDTOList = new ArrayList<>();
		for (Property property : propertyList) {
			propertyDTOList.add(toDTO(property));
		}
		return propertyDTOList;
	}

}