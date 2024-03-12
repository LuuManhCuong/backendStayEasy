package com.backend.stayEasy.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.stayEasy.repository.StatisticsRepository;

@Component
public class ScheduleTasks {
	
	@Autowired
    private StatisticSevice statisticSevice;
	@Autowired
	private StatisticsRepository statisticsRepository;


}
