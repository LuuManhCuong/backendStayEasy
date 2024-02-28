package com.backend.stayEasy.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class Transaction {
    private UUID id;
    private UUID bookingId;
    private Double price;
    private  String currency;
    private String method;
    private String intent;
    private String description ;
}
