package com.backend.stayEasy.dto;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class BookingParam {
    private Date checkIn;
    private Date checkOut;
    private UUID propertyId;
    private UUID userId;
    private int numberOfAdult;
    private int numberOfChildren;
    private int numberOfPets;
    private int numberOfInfants ;
    private  int numberNight;
    private Double price;
    private  String currency;
    private String method;
    private String intent;
    private String description ;

}
