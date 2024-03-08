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

	// Chạy vào ngày cuối cùng của tháng lúc 11:30:00 PM
	@Scheduled(cron = "0 30 23 L * ?")
    public void calculateAndSaveMonthlyStatistics() {
        
        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();
        Date todayDate = Date.valueOf(currentDate);
        
        // Lấy tháng hiện tại
        int currentMonth = currentDate.getMonthValue();
        
        // Lấy ngày đầu của tháng hiện tại
        LocalDate firstDayOfCurrentMonth = LocalDate.of(currentDate.getYear(), currentMonth, 1);
        Date firstDateOfCurrentMonth = Date.valueOf(firstDayOfCurrentMonth);
        
        // Tính và lưu thống kê cho tháng hiện tại
        Statistics currentMonthStatistics = statisticSevice.calculateRevenueForMonth(todayDate, firstDateOfCurrentMonth);
        statisticsRepository.save(currentMonthStatistics);
    }
}
