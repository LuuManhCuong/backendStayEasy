package com.backend.stayEasy.entity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="property")
public class Property {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "property_id")
    private UUID propertyId;
	@Column(name = "property_name")
	private String propertyName;
    private String description;
    private String thumbnail;
    private String address;
    private Float price;
    private Float rating;
    @Column(name = "is_null")
    private boolean isNull;
    @Column(name = "num_guests")
    private int numGuests;
    @Column(name = "discount")
    private int discount;
    @ManyToOne()
    private User user;
    
    @OneToMany(mappedBy =  "property")
    private Set<Like> likes;
    
    @OneToMany(mappedBy = "property")
    private Set<Feedback> feedbacks;
    
    @OneToMany(mappedBy = "property")
    private Set<Images> images;
    
    @OneToMany(mappedBy = "property")
    private Set<BookingDetail> bookingDetails;
    
    @OneToMany(mappedBy = "property")
    private Set<PropertyCategory> propertyCategories;
    
    @OneToMany(mappedBy = "property")
    private Set<PropertyUilitis> propertyUilitis;
}
