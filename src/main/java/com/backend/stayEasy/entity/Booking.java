package com.backend.stayEasy.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;

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
    private boolean status;

}
