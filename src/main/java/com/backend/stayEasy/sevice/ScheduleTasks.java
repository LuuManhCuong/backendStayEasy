package com.backend.stayEasy.sevice;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.backend.stayEasy.entity.Statistics;
import com.backend.stayEasy.repository.StatisticsRepository;

@Component
public class ScheduleTasks {
	
	@Autowired
    private StatisticSevice statisticSevice;
	@Autowired
	private StatisticsRepository statisticsRepository;


}
