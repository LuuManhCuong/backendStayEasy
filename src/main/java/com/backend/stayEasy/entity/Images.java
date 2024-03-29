package com.backend.stayEasy.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Images {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	
    private UUID imageId;
	private String url;
	private String description;
	
	@ManyToOne()
	private Property property;
}
