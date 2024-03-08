package com.backend.stayEasy.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.backend.stayEasy.entity.Feedback;
import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.entity.PropertyUilitis;
import com.backend.stayEasy.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
	private UUID feedbackId;
	private String content;
	private String username;
	private String avatar;
	private UUID userId;
	private UUID propertyId;
}
