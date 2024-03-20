package com.backend.stayEasy.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.convertor.UtilitiesConverter;
import com.backend.stayEasy.dto.UtilitiesDTO;
import com.backend.stayEasy.entity.Utilities;
import com.backend.stayEasy.repository.UtilitiesRepository;
import com.backend.stayEasy.sevice.impl.IUtilitiesService;

@Service
public class UtilService implements IUtilitiesService{
	
	@Autowired
	private UtilitiesRepository utilitiesRepository;
	
	@Autowired
	private UtilitiesConverter utilitiesConverter;

	@Override
	public List<UtilitiesDTO> findAll() {
		return utilitiesConverter.arrayToDTO(utilitiesRepository.findAll());
	}
	
	
}
