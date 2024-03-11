package com.backend.stayEasy.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.backend.stayEasy.dto.PropertyUtilitiesDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import jakarta.persistence.*;

import lombok.Data;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "property")

public class Property {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "property_id")
	private UUID propertyId;
	@Column(name = "property_name", columnDefinition = "nvarchar(255)")
	private String propertyName;
	@Column(columnDefinition = "ntext")
	private String description;
	private String thumbnail;
	@Column(columnDefinition = "nvarchar(255)")
	private String address;
	private Float price;
	private Float rating;
	@Column(name = "is_null")
	private boolean isNull;
	@Column(name = "num_guests")
	private int numGuests;
	@Column(name = "discount")
	private int discount;
	@Column(name = "create_at")
	private Date createAt;

	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Like> likes;

	@OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Feedback> feedbacks;

	@OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Images> images;

	@OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<BookingDetail> bookingDetails;

	@OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PropertyCategory> propertyCategories;

	@OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PropertyUilitis> propertyUilitis;

}