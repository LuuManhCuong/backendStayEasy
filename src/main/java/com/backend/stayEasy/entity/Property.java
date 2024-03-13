package com.backend.stayEasy.entity;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


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
	private int numBedRoom;
	private int numBathRoom;
	@Column(name = "is_null")
	private boolean isNull;
	@Column(name = "num_guests")
	private int numGuests;
	@Column(name = "discount")
	private int discount;
	@Column(name = "create_at")
	private Date createAt;
	@Column (name = "service_fee")
	private int serviceFee;
	

	@ManyToOne
	private User user;

//	@OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<Feedback> feedbacks;
	@OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

	private Set<Like> likes;


	@OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Images> images;

//	@OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<Feedback> feedbacks;

	@OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PropertyCategory> propertyCategories;

	@OneToMany(mappedBy = "property")
	private Set<PropertyUilitis> propertyUilitis;

//	@ManyToMany
//	@JoinTable(name = "category_property", joinColumns = @JoinColumn(referencedColumnName = "property_id"), inverseJoinColumns = @JoinColumn(referencedColumnName = "category_id"))
//	private Set<Category> categories = new HashSet<>();
}