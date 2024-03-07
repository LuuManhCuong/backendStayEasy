package com.backend.stayEasy.dto;

import java.util.Set;
import java.util.UUID;

import com.backend.stayEasy.entity.Feedback;
import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.entity.PropertyUilitis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagesDTO {
	private UUID imageId;
	private String url;
	private String description;
	private UUID propertyId;
}
