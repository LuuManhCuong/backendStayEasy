package com.backend.stayEasy.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyUtilitiesDTO {
	private UUID utilitiesId;
	private String type;
    private String utilitiesName;
}
