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
public class CategoryDTO {
	private UUID categoryId;
	private String categoryName;
	private List<UUID> propertyId;
}
