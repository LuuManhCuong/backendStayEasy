package com.backend.stayEasy.api;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.repository.ExploreRepository;

@RestController
@CrossOrigin
public class ExploreApi {
	@Autowired
	private ExploreRepository exploreRepository;
	
	@GetMapping("/explore")
	public List<Property> getAllProperties() {
        return exploreRepository.findAll();
    }
	
	@GetMapping("/explore/search")
	public List<Property> searchExplore(@RequestParam("keySearch") String keySearch) {
		System.out.println("keysearch: " + keySearch);
		List<Property> searchResults = exploreRepository.findByPropertyNameContainingIgnoreCase(keySearch);
        return searchResults;
	}

}
