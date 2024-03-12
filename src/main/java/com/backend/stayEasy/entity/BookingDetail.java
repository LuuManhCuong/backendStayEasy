package com.backend.stayEasy.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Booking_detail")
public class BookingDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "booking_detail_id")
	private UUID bookingDetailID;

	@Column(name = "checkin_date")
	private LocalDateTime checkinDate;

	@Column(name = "checkout_date")
	private LocalDateTime checkoutDate;
<<<<<<< HEAD
	@Column(name = "total_price")
	private Float totalPrice;
=======
	
	@Column(name = "total_price")
	private Float totalPrice;
	
	@Column(name = "status")
>>>>>>> origin/namhh-update-infor
	private boolean status;

	@ManyToOne()
	private Booking booking;
<<<<<<< HEAD
//
//	@ManyToOne
//	private Property property;
=======

	@ManyToOne
	private Property property;
>>>>>>> origin/namhh-update-infor

}
