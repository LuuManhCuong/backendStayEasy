package com.backend.stayEasy.sevice;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.entity.Images;
import com.backend.stayEasy.repository.IImageRepository;


@Service
public class ImageService implements IImageService{
	
	@Autowired
	private IImageRepository imageRepository;

	@Override
	public List<Images> getPropertyImage(UUID propertyId) {
		// TODO Auto-generated method stub
		return imageRepository.findByPropertyPropertyId(propertyId);
	}

}
