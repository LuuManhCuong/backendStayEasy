package com.backend.stayEasy.dto;

import java.sql.Date;
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
    private Date createAt;
    private Float rating;
    private UserDTO owner;
    private List<ImagesDTO> imagesList;
    private List<CategoryDTO> categories;
    private Set<FeedbackDTO> feedbackList;
    private Set<PropertyUtilitiesDTO> propertyUtilitis;
    private Set<LikeRequestDTO> likeList;
}