package com.backend.stayEasy.convertor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.UtilitiesDTO;
import com.backend.stayEasy.entity.Utilities;

@Component
public class UtilitiesConverter {
	
	public UtilitiesDTO toDTO(Utilities utilities) {
		UtilitiesDTO utilitiDTO = new UtilitiesDTO();
		utilitiDTO.setUtilitiId(utilities.getUtilitiId());
		utilitiDTO.setUtilitiesName(utilities.getUtilitiesName());
		utilitiDTO.setType(utilities.getType());
		
		return utilitiDTO;
	}
	
	public List<UtilitiesDTO> arrayToDTO(List<Utilities> utilitiesList) {
		List<UtilitiesDTO> utilDTOList = new ArrayList<>();
		for (Utilities util : utilitiesList) {
			utilDTOList.add(toDTO(util));
		}
		return utilDTOList;
	}

}
