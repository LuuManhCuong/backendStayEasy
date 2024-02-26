package com.backend.stayEasy.convertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.FeedbackDTO;
import com.backend.stayEasy.entity.Feedback;


@Component
public class FeedbackConverter {
	@Autowired
	private UserConverter userConverter;
	
	public FeedbackDTO toDTO(Feedback feedback) {
		FeedbackDTO feedbackDTO = new FeedbackDTO();
		feedbackDTO.setComment(feedback.getComment());
		feedbackDTO.setCreatedAt(feedback.getCreatedAt());
		feedbackDTO.setFeedbackId(feedback.getFeedbackId());
		feedbackDTO.setRating(feedback.getRating());
		feedbackDTO.setUserId(userConverter.toDTO(feedback.getUser()));
		feedbackDTO.setPropertyId(feedback.getProperty().getPropertyId());
		return feedbackDTO;
	}
}
