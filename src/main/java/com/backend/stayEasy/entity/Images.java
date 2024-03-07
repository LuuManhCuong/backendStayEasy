package com.backend.stayEasy.entity;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="images")
public class Images {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID imageId;
	private String url;
	private String description;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "property_property_id", referencedColumnName = "property_id", nullable = false)
	private Property property;
}
