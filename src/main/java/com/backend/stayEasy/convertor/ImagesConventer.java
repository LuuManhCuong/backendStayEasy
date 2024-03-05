package com.backend.stayEasy.convertor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.ImagesDTO;
import com.backend.stayEasy.entity.Images;



@Component
public class ImagesConventer {
	public ImagesDTO toDTO(Images images) {
		ImagesDTO imagesDTO = new ImagesDTO();
		imagesDTO.setDescription(images.getDescription());
		imagesDTO.setImageId(images.getImageId());
		imagesDTO.setUrl(images.getUrl());
		return imagesDTO;
	}
	
	public List<ImagesDTO> arrayToDTO(List<Images> imagesList) {
		List<ImagesDTO> imagesDTOList = new ArrayList<>();
		for (Images image : imagesList) {
			imagesDTOList.add(toDTO(image));
		}
		return imagesDTOList;
	}
}
