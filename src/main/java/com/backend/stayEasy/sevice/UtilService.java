package com.backend.stayEasy.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.entity.Utilities;
import com.backend.stayEasy.repository.UtilitiesRepository;

@Service
public class UtilService implements IUtilService{
	
	@Autowired
	private UtilitiesRepository utilitiesRepository;

	@Override
	public List<Utilities> findAll() {
		return utilitiesRepository.findAll();
	}

}
