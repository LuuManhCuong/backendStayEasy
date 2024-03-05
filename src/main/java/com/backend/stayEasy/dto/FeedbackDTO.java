package com.backend.stayEasy.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
	private UUID feedbackId;
	private String comment;
	private int rating;
	private LocalDateTime createdAt;
	private UserDTO userId;
	private UUID propertyId;
}
