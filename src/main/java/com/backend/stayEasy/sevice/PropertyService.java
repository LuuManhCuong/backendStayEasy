package com.backend.stayEasy.sevice;

import com.backend.stayEasy.convertor.LikeConverter;
import com.backend.stayEasy.convertor.PropertyConverter;
import com.backend.stayEasy.dto.CategoryDTO;
import com.backend.stayEasy.dto.ImagesDTO;
import com.backend.stayEasy.dto.LikeRequestDTO;
import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.*;
import com.backend.stayEasy.repository.*;
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
		Optional<Property> propertyOptional = propertyRepository.findById(propertyId);

		if (propertyOptional.isPresent()) {
			Property existingProperty = propertyOptional.get();
			Property updatedProperty = propertyConverter.toEntity(updatePropertyDTO); // Tạo một bản sao mới từ DTO đã
																						// được cập nhật

			// Copy các thông tin từ updatedProperty vào existingProperty
			existingProperty.setPropertyName(updatedProperty.getPropertyName());
			existingProperty.setDescription(updatedProperty.getDescription());
			existingProperty.setThumbnail(updatedProperty.getThumbnail());
			existingProperty.setPrice(updatedProperty.getPrice());
			existingProperty.setNumGuests(updatedProperty.getNumGuests());
			existingProperty.setDiscount(updatedProperty.getDiscount());

			for (CategoryDTO categoryDTO : updatePropertyDTO.getCategories()) {
				PropertyCategory temp = new PropertyCategory();
				temp.setProperty(existingProperty);
				Optional<Category> categoryOp = categoryRepository.findById(categoryDTO.getCategoryId());
				if (categoryOp.isPresent()) {
					Category category = categoryOp.get();
					temp.setCategory(category);
				}
				// Kiểm tra xem danh sách propertyCategory hiện tại của property đã có category
				// này hay chưa
				// Nếu chưa có, thì thêm vào danh sách, nếu đã có thì không cần thêm
				for (PropertyCategory propertyCategory : existingProperty.getPropertyCategories()) {
					if (!(propertyCategory.getCategory().getCategoryId() == temp.getCategory().getCategoryId())) {
						existingProperty.getPropertyCategories().add(temp);
					}
				}
			}

//		        existingProperty.getImages().clear();
//
//		        // Thêm hình ảnh mới vào bất động sản
//		        for (ImagesDTO imagesDTO : updatePropertyDTO.getImagesList()) {
//		            Images newImage = new Images(imagesDTO.getUrl(), imagesDTO.getDescription(), existingProperty);
//		            existingProperty.getImages().add(newImage);
//		        }

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

			// Thêm hình ảnh mới vào bất động sản
			for (ImagesDTO imagesDTO : updatePropertyDTO.getImagesList()) {
				boolean exists = false;
				for (Images existingImage : existingProperty.getImages()) {
					if (existingImage.getUrl().equals(imagesDTO.getUrl())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					Images newImage = new Images(imagesDTO.getUrl(), imagesDTO.getDescription(), existingProperty);
					existingProperty.getImages().add(newImage);
				}
			}

			// Lưu bản sao đã được cập nhật vào cơ sở dữ liệu
			Property result = propertyRepository.save(existingProperty);

			// Convert và trả về DTO của đối tượng đã được cập nhật
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