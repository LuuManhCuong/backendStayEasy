package com.backend.stayEasy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagesDTO {
	private UUID imageId;
	private String url;
	private String description;
}
