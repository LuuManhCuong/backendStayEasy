package com.backend.stayEasy.api;


import com.backend.stayEasy.convertor.LikeConverter;
import com.backend.stayEasy.dto.LikeRequestDTO;
import com.backend.stayEasy.repository.LikeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public ResponseEntity<String> handleLike(@RequestBody LikeRequestDTO likeRequest) {
//		viêt hàm jpa để lưu vào bảng like
		likeRepository.save(likeConverter.convertToLikeEntity(likeRequest));
		return ResponseEntity.ok("Liked post with ID: " + likeRequest.getIdPost() + " by user with ID: " + likeRequest.getIdUser());
    }
	
	
	@Transactional
	 @DeleteMapping("/unlike")
	    public ResponseEntity<String> unlikeProperty(@RequestParam UUID idPost, @RequestParam UUID idUser) {
	        likeRepository.deleteByPropertyPropertyIdAndUserId(idPost, idUser);
	        return ResponseEntity.ok("Successfully unliked the property.");
	    }
}
