package com.backend.stayEasy.convertor;

import org.springframework.stereotype.Component;

import com.backend.stayEasy.dto.StatisticsDTO;
import com.backend.stayEasy.entity.Statistics;

@Component
public class StatisticsConverter {

	public StatisticsDTO toDTO(Statistics statistics) {
		StatisticsDTO statisticsDTO = new StatisticsDTO();
		
		statisticsDTO.setStatisticsId(statistics.getStatisticsId());
		statisticsDTO.setDate(statistics.getDate());
		statisticsDTO.setRevenue(statistics.getRevenue());
		statisticsDTO.setTotalAccount(statistics.getTotalAccount());
		statisticsDTO.setTotalBookings(statistics.getTotalBookings());
		statisticsDTO.setTotalPost(statistics.getTotalPost());
		statisticsDTO.setTotalCancelBooking(statistics.getTotalCancelBooking());
//		statisticsDTO.setVisitCount(statistics.getVisitCount());
		
		return statisticsDTO;
	}
}
