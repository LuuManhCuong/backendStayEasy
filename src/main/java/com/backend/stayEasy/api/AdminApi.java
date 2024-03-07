package com.backend.stayEasy.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.stayEasy.dto.StatisticsDTO;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/stayeasy/admin")
public class AdminApi {
	
	@GetMapping("/revenue")
	public StatisticsDTO getRevenueByMonth() {
		StatisticsDTO statisticsDTO = new StatisticsDTO();
		statisticsDTO.setMonth(0);
		return statisticsDTO;
	}
}
