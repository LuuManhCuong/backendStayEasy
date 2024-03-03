package com.backend.stayEasy.convertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.FeedbackDTO;
import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.entity.Feedback;
import com.backend.stayEasy.entity.Property;
import com.backend.stayEasy.entity.User;


@Component
public class FeedbackConverter {
	@Autowired
	private UserConverter userConverter;
	
	public FeedbackDTO toDTO(Feedback feedback) {
		FeedbackDTO feedbackDTO = new FeedbackDTO();
		feedbackDTO.setContent(feedback.getContent());
		feedbackDTO.setFeedbackId(feedback.getFeedbackId());
		feedbackDTO.setUserId(feedback.getUserId());
		feedbackDTO.setPropertyId(feedback.getPropertyId());
		return feedbackDTO;
	}
	
	public Feedback toEntity(FeedbackDTO feedbackDTO) {
		Feedback feedback = new Feedback();
		
		feedback.setContent(feedbackDTO.getContent());
		feedback.setUserId(feedbackDTO.getUserId());
		feedback.setAvatar(feedbackDTO.getAvatar());
		feedback.setUsername(feedbackDTO.getUsername());
		feedback.setPropertyId(feedbackDTO.getPropertyId());;
		return feedback;
	}
}
