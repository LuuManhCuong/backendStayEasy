package com.backend.stayEasy.entity;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;
=======
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
>>>>>>> origin/namhh-update-infor

@Entity
@Getter
@Setter
@Table(name = "Booking")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Booking {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID bookingId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "property_id", nullable = false)
	private Property property;
	@Column(name = "check-in")
	private Date checkIn;
	@Column(name = "check-out")
	private Date checkOut;
	@Column(name = "date-booking")
	private Date dateBooking;
//	@OneToMany(mappedBy = "booking")
//	private Set<BookingGuest> bookingGuests;
	
//	@OneToMany(mappedBy = "booking")
//	private Set<BookingDetail> bookingDetails;
	@Column(name = "numNight")
	private int numNight;
	@Column(name = "total_price")
<<<<<<< HEAD
    private Double totalPrice;
	@Column(name = "status")
	private Boolean status;
	@Column(name = "num-guest")
	private int numGuest;
	@Column(name = "cancel")
	private Boolean cancel;
=======
    private Float totalPrice;
	
	@Column(name = "status")
    private boolean status;

>>>>>>> origin/namhh-update-infor
}
