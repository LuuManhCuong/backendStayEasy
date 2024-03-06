package com.backend.stayEasy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

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
    private Set<ImagesDTO> imagesList;
    private Set<FeedbackDTO> feedbackList;
    private Set<PropertyUtilitiesDTO> propertyUtilitis;
    private Set<LikeRequestDTO> likeList;
}
