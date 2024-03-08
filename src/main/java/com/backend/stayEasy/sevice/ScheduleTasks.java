package com.backend.stayEasy.sevice;

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

    // Chạy mỗi tháng vào ngày đầu tiên của tháng lúc 12:00 AM
    @Scheduled(cron = "0 0 0 1 * *")
    public void calculateAndSaveMonthlyStatistics() {
        Statistics statistics = statisticSevice.calculateRevenueForCurrentMonth();
        statisticsRepository.save(statistics);
    }
}
