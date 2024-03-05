package com.backend.stayEasy.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="feedback")
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID feedbackId;
	private String comment;
	private int rating;
	private LocalDateTime createdAt;
	
	@ManyToOne()
	private User user;
	
	@ManyToOne()
	private Property property;
}
