package com.backend.stayEasy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private UUID bookingId ;
    private  UUID  userId;
    private  UUID propertyId;
    private Date checkIn;
    private Date checkOut;
    private Date dateBooking;
    private int numOfNight;
    private Double total;
    private  Boolean status;
    private String propertyName;
    private  int numOfGuest;
//    private Set<GuestType> guestTypes;
}