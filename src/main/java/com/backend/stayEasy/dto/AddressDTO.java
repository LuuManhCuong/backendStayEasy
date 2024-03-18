package com.backend.stayEasy.dto;

import lombok.Data;

@Data
public class AddressDTO {
	private String street;
	private String ward;
	private String district;
	private String city;
	private String country;
}
