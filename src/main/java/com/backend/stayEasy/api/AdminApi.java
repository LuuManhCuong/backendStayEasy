package com.backend.stayEasy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.convertor.StatisticsConverter;
import com.backend.stayEasy.dto.StatisticsDTO;
import com.backend.stayEasy.entity.Statistics;
import com.backend.stayEasy.sevice.StatisticSevice;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stayeasy/admin")
public class AdminApi {
	
	@Autowired
	private StatisticSevice statisticSevice;
	@Autowired
	private StatisticsConverter statisticsConverter;
	@GetMapping("/revenue")
	public StatisticsDTO getRevenueByMonth() {
		System.out.println("home");
		Statistics statistics = statisticSevice.calculateRevenueForCurrentMonth();
		
		System.out.println("dt0: " + statisticsConverter.toDTO(statistics));
		return statisticsConverter.toDTO(statistics);
	}
	

}
