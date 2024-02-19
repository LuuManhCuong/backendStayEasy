package com.backend.stayEasy.sevice;

import java.util.List;
import java.util.UUID;

import com.backend.stayEasy.entity.Images;



public interface IImageService {
	List<Images> getPropertyImage(UUID userId);
}
