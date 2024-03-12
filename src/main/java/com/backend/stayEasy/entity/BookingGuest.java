package com.backend.stayEasy.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class BookingGuest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID bookingId;
    
    @ManyToOne()
    private Booking booking;
    
    @ManyToOne()
    private GuestType guestType;

    private int numGuest;
}
