package com.backend.stayEasy.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PropertyUilitis {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID utilityId;

    private String utilityName;
    
    @ManyToOne()
    private Property property;
    
    @ManyToOne()
    private Utilities utilities;
}
