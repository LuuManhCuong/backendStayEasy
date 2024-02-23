package com.backend.stayEasy.dto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.backend.stayEasy.entity.Feedback;
import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.entity.PropertyUilitis;
import com.backend.stayEasy.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {
	private UUID propertyId;
	private String propertyName;
    private String description;
    private String thumbnail;
    private String address;
    private Float price;
    private boolean isNull;
    private int numGuests;
    private int discount;
    private Float rating;
    private UserDTO owner;
    private Set<Images> imagesList;
    private Set<Feedback> feedbackList;
    private Set<PropertyUilitis> propertyUtilitis;
}
