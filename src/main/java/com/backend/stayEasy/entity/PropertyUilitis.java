package com.backend.stayEasy.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="property_uilitis")
public class PropertyUilitis {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID propertyUtilitiesId;

    private int quantity;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Property property;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Utilities utilities;
}