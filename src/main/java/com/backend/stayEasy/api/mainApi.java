package com.backend.stayEasy.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.convertor.LikeConverter;
import com.backend.stayEasy.dto.LikeRequestDTO;
import com.backend.stayEasy.entity.Like;
import com.backend.stayEasy.repository.LikeRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stayeasy")
public class MainApi {
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private LikeConverter likeConverter;
	
	@GetMapping
	public String homePage() {
		return "WELCOME TO STAYEASY.";
	}
	
	@PostMapping("/like")
    public String handleLike(@RequestBody LikeRequestDTO likeRequest) {
//		viêt hàm jpa để lưu vào bảng like
		likeRepository.save(likeConverter.convertToLikeEntity(likeRequest));
		 return "Liked post with ID: " + likeRequest.getIdPost() + " by user with ID: " + likeRequest.getIdUser();
    }
}
