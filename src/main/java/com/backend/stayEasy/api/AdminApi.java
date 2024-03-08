package com.backend.stayEasy.api;

import com.backend.stayEasy.convertor.StatisticsConverter;
import com.backend.stayEasy.dto.StatisticsDTO;
import com.backend.stayEasy.entity.Statistics;
import com.backend.stayEasy.sevice.StatisticSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stayeasy/admin")
public class AdminApi {

	@Autowired
	private StatisticSevice statisticSevice;
	@Autowired
	private StatisticsConverter statisticsConverter;
	@GetMapping("/revenue")
	public List<StatisticsDTO> getRevenueByMonth() {
		List<StatisticsDTO> statisticsDTOs = new ArrayList<>();
		List<Statistics> statisticsList = statisticSevice.calculateRevenueForCurrentAndPreviousMonth();
		for (Statistics statisticsItem : statisticsList) {
			statisticsDTOs.add(statisticsConverter.toDTO(statisticsItem));
		}
		return statisticsDTOs;
	}
	

}
