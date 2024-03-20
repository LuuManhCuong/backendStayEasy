package com.backend.stayEasy.entity;

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
@Table(name = "property_uilitis")
public class PropertyUilitis {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID propertyUtilitiesId;
    private int quantity;
    
    @ManyToOne()
    private Property property;
    
    @ManyToOne()
    private Utilities utilities;
}