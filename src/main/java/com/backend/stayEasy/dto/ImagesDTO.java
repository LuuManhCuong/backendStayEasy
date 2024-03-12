package com.backend.stayEasy.dto;

<<<<<<< HEAD
=======
import java.util.UUID;

>>>>>>> origin/namhh-update-infor
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
	private UUID propertyId;
}
