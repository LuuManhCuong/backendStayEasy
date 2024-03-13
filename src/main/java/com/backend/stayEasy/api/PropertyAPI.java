package com.backend.stayEasy.api;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.convertor.LikeConverter;
import com.backend.stayEasy.convertor.PropertyConverter;
import com.backend.stayEasy.dto.LikeRequestDTO;
import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.entity.Like;
import com.backend.stayEasy.entity.Property;

import com.backend.stayEasy.repository.ExploreRepository;
import com.backend.stayEasy.repository.IPropertyRepository;
import com.backend.stayEasy.repository.LikeRepository;
import com.backend.stayEasy.sevice.IPropertyService;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping(value = "/api/v1/stayeasy/property", produces = "application/json")
public class PropertyAPI {

	@Autowired
	private IPropertyService propertyService;

	@Autowired
	private ExploreRepository exploreRepository;

	@Autowired
	private IPropertyRepository propertyRepository;

	@Autowired
	private PropertyConverter propertyConverter;

//	@Autowired
//	private BookingRepository bookingRepository;

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private LikeConverter likeConverter;

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

	@GetMapping("/category/{category}")
	public List<PropertyDTO> getPropertyByCategory(@PathVariable("category") UUID categoryId) {
		System.out.println(categoryId);
		return propertyService.findByCategory(categoryId);
	}

	@GetMapping("/search/suggest")
	public List<PropertyDTO> searchAddressSuggest(@RequestParam("address") String address) {
		System.out.println("keysearch suggest: " + address);
		List<Property> searchResults = exploreRepository.findByAddressContainingIgnoreCaseOrderByRatingDesc(address);
		List<PropertyDTO> propertyDTOs = new ArrayList<>();
		for (Property property : searchResults) {
			propertyDTOs.add(propertyConverter.toDTO(property));
		}
		return propertyDTOs;
	}

	@GetMapping("/search")
	public List<PropertyDTO> searchPropertyByAddressAndDate(@RequestParam("address") String address,
			@RequestParam("checkin") String checkin, @RequestParam("checkout") String checkout) {

		// Định dạng chuỗi ngày thành đối tượng Date
		SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
		Date checkinDate = null;
		Date checkoutDate = null;
		try {
			checkinDate = new Date(formatter.parse(checkin).getTime());
			checkoutDate = new Date(formatter.parse(checkout).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<PropertyDTO> propertyDTOs = new ArrayList<>();
		List<Property> properties = propertyRepository.findAvailableProperties(checkinDate, checkoutDate, address);
		for (Property property : properties) {
			PropertyDTO propertyDTO = propertyConverter.toDTO(property);
			List<Like> likes = likeRepository.findByPropertyPropertyId(property.getPropertyId()); // get like tương ứng
																									// mỗi property
			Set<LikeRequestDTO> likeRequestDTOs = likeConverter.arraytoDTO(likes);
			propertyDTO.setLikeList(likeRequestDTOs);
			propertyDTOs.add(propertyDTO);
		}

//	        System.out.println("address: " + address);
//	        System.out.println("checkin: " + checkinDate);
//	        System.out.println("checkout: " + checkoutDate);

		return propertyDTOs;
	}
}
