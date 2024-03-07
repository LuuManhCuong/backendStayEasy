package com.backend.stayEasy.convertor;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.CategoryDTO;
import com.backend.stayEasy.dto.ImagesDTO;
import com.backend.stayEasy.dto.LikeRequestDTO;
import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.BookingDetail;
import com.backend.stayEasy.entity.Category;
import com.backend.stayEasy.entity.Feedback;
import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.entity.Like;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.entity.PropertyCategory;
import com.backend.stayEasy.entity.PropertyUilitis;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.repository.ICategoryRepository;
import com.backend.stayEasy.repository.UserRepository;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Component
public class PropertyConverter {

	@Autowired
	private ImagesConventer imagesConventer;

	@Autowired
	private CategoryConverter categoryConverter;

	@Autowired
	private ICategoryRepository categoryRepository;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private UserRepository userRepository;

	public PropertyDTO toDTO(Property property) {
		List<ImagesDTO> listImages = new ArrayList<>();
		List<UUID> categoryIds = new ArrayList<>();
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
		if (!property.getCategories().isEmpty()) {
			for (Category c : property.getCategories()) {
				categoryIds.add(c.getCategoryId());
			}
			System.out.println("iddd: " + categoryIds);
		}
		propertyDTO.setCategoryIds(categoryIds);

		propertyDTO.setThumbnail(property.getThumbnail());
		if (!property.getImages().isEmpty()) {
			for (Images i : property.getImages()) {
				listImages.add(imagesConventer.toDTO(i));
			}
		}
		propertyDTO.setImagesList(listImages);
		propertyDTO.setOwnerId(property.getUser().getId());

		if (property.getUser() != null) {
			propertyDTO.setOwner(userConverter.toDTO(property.getUser()));
		}
		return propertyDTO;
	}

	public Property toEntity(PropertyDTO propertyDTO) {

		Property property = new Property();
		property.setAddress(propertyDTO.getAddress());
		property.setDescription(propertyDTO.getDescription());
		property.setDiscount(propertyDTO.getDiscount());
//		property.setNull(propertyDTO.isNull());
		property.setNumGuests(propertyDTO.getNumGuests());
		property.setPrice(propertyDTO.getPrice());
		property.setPropertyId(propertyDTO.getPropertyId());
		property.setPropertyName(propertyDTO.getPropertyName());
//		property.setRating(propertyDTO.getRating());
		property.setThumbnail(propertyDTO.getThumbnail());
		property.setUser(userRepository.findById(propertyDTO.getOwnerId()).get());

		return property;

	}

//	no

	public PropertyDTO toDTO(Property property, List<LikeRequestDTO> likeRequestDTOlist) {
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
		Set<LikeRequestDTO> likeSet = new HashSet<>(likeRequestDTOlist);
		propertyDTO.setLikeList(likeSet);
		return propertyDTO;
	}

	public List<PropertyDTO> arrayToDTO(List<Property> propertyList, List<LikeRequestDTO> likeRequestDTOlist) {
		List<PropertyDTO> propertyDTOList = new ArrayList<>();
		for (Property property : propertyList) {
			propertyDTOList.add(toDTO(property, likeRequestDTOlist));
		}
		return propertyDTOList;
	}

}
