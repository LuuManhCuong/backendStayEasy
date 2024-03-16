package com.backend.stayEasy.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Address {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private UUID id;
	
	private String street;
	private String ward;
	private String district;
	private String city;
	private String country;
	
	@OneToOne(mappedBy = "address")
    private User user;
}
