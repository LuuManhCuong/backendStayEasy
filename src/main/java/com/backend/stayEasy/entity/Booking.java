package com.backend.stayEasy.entity;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity

public class Booking {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID bookingId;
	
	@ManyToOne()
	private User user;
	
	@OneToMany(mappedBy = "booking")
	private Set<BookingGuest> bookingGuests;
	
	@OneToMany(mappedBy = "booking")
	private Set<BookingDetail> bookingDetails;
	
	@Column(name = "total_price")
    private Float totalPrice;
	
	@Column(name = "status")
    private boolean status;

}
