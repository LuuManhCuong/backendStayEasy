package com.backend.stayEasy.api;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.dto.DataPropertyExploreDTO;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.repository.ExploreRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stayeasy")
public class ExploreApi {
	@Autowired
	private ExploreRepository exploreRepository;
	
	@GetMapping("/explore")
	public List<Property> getAllProperties() {
        return exploreRepository.findAll();
    }
	
	 @GetMapping("/explore/search")
	    public DataPropertyExploreDTO searchExplore(
	            @RequestParam("keySearch") String keySearch,
	            @RequestParam("page") int page,
	            @RequestParam("size") int size
	    ) {
	        System.out.println("keysearch: " + keySearch);
	        System.out.println("page: " + page);
	        System.out.println("size: " + size);
	        
	        // Tạo đối tượng Pageable từ page và size
	        Pageable pageable = PageRequest.of(page, size);
	        
	        // Lấy tổng số lượng bản ghi
	        long totalCount = exploreRepository.count();
	        
	        // Trả về kết quả truy vấn kèm theo tổng số lượng bản ghi
	        Page<Property> properties = exploreRepository.findByPropertyNameOrDescriptionContainingIgnoreCase(keySearch, pageable);
	        return new DataPropertyExploreDTO(totalCount, properties);
	    }
	
	
	
	@GetMapping("/explore/search/suggest")
	public List<Property> searchExploreSuggest(@RequestParam("keySearch") String keySearch) {
		System.out.println("keysearch: " + keySearch);
		List<Property> searchResults = exploreRepository.findByPropertyNameOrDescriptionContainingIgnoreCaseOrderByRatingDesc(keySearch);
        return searchResults;
	}

}