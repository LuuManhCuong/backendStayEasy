package com.backend.stayEasy.entity;

import java.sql.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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

	@Column(name = "numNight")
	private int numNight;
	
	@Column(name = "total_price")
    private Double totalPrice;
	
	@Column(name = "status")
	private Boolean status;
	
	@Column(name = "num-guest")
	private int numGuest;
	
	@Column(name = "cancel")
	private Boolean cancel;
}
