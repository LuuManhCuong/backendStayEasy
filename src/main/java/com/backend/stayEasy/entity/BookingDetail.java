package com.backend.stayEasy.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

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
	@Column(name = "total_price")
	private Float totalPrice;
	private boolean status;

	@ManyToOne()
	private Booking booking;
//
//	@ManyToOne
//	private Property property;

}
