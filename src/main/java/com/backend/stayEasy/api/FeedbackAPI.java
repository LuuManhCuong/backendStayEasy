package com.backend.stayEasy.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.stayEasy.convertor.FeedbackConverter;
import com.backend.stayEasy.convertor.UserConverter;
import com.backend.stayEasy.dto.FeedbackDTO;
import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.entity.Feedback;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.repository.FeedbackRepository;
import com.backend.stayEasy.repository.IPropertyRepository;
import com.backend.stayEasy.repository.UserRepository;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/stayeasy/feedback")
public class FeedbackAPI {
	
	
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	private IPropertyRepository propertyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private UserConverter userConverter;
	
	@Autowired 
	private FeedbackConverter feedbackConverter;
	
	  @Autowired
	    private SimpMessagingTemplate messagingTemplate;

	    @MessageMapping("/feedback/{propertyId}")
	    @SendTo("/api/v1/stayeasy/topic/feedback/{propertyId}")
	    public Feedback sendFeedback(@Payload FeedbackDTO feedbackDTO, @DestinationVariable UUID propertyId) {        
	    	Feedback feedback = feedbackConverter.toEntity(feedbackDTO);
	    	feedback.setCreateAt(LocalDateTime.now());
	    	System.out.println("feedback: " + feedback.getContent());
	    	feedbackRepository.save(feedback);
	    	return feedback;
	    }
	
	    
	    
	    @GetMapping("/get/{propertyId}")
	    public ResponseEntity<List<Feedback>> getAllFeedbacksByPropertyId(@PathVariable UUID propertyId) {
	        List<Feedback> feedbacks = feedbackRepository.findByPropertyIdOrderByCreateAtDesc(propertyId);
	        return ResponseEntity.ok(feedbacks);
	    }
	    
	 


	    
	    
}
 