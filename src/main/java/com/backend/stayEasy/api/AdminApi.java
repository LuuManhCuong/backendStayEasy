package com.backend.stayEasy.api;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.convertor.StatisticsConverter;
import com.backend.stayEasy.dto.DailyRevenueDTO;
import com.backend.stayEasy.dto.StatisticsDTO;
import com.backend.stayEasy.entity.Statistics;
import com.backend.stayEasy.repository.BookingRepository;
import com.backend.stayEasy.sevice.StatisticSevice;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stayeasy/admin")
public class AdminApi {
	
	@Autowired
	private StatisticSevice statisticSevice;
	@Autowired
	private StatisticsConverter statisticsConverter;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@GetMapping("/revenue")
	public List<StatisticsDTO> getRevenueByMonth() {
		List<StatisticsDTO> statisticsDTOs = new ArrayList<>();
		List<Statistics> statisticsList = statisticSevice.calculateRevenueForCurrentAndPreviousMonth();
		for (Statistics statisticsItem : statisticsList) {
			statisticsDTOs.add(statisticsConverter.toDTO(statisticsItem));
		}
		return statisticsDTOs;
	}
	
//	@GetMapping("/revenue/daily")
//	public List<DailyRevenueDTO> getDailyRevenues(@RequestParam("date") Date date) {
//	    List<DailyRevenueDTO> dailyRevenues = bookingRepository.findDailyRevenueByMonthAndYear(date);
//	    return dailyRevenues;
//	}

	

	 @GetMapping("/revenue/daily")
	    public Map<String, List<DailyRevenueDTO>> getDailyRevenues(@RequestParam("date") Date date) {
	        // Chuyển đổi Date SQL thành LocalDate
	        LocalDate localDate = date.toLocalDate();

	        // Lấy tháng và năm của ngày được cung cấp
	        int currentMonth = localDate.getMonthValue();
	        int currentYear = localDate.getYear();

	        // Truy vấn doanh thu hàng ngày của tháng hiện tại
	        List<DailyRevenueDTO> currentDailyRevenue = bookingRepository.findDailyRevenueByMonthAndYear(date);

	        // Truy vấn doanh thu hàng ngày của tháng trước
	        int previousMonth = currentMonth - 1;
	        int previousYear = currentYear;
	        if (previousMonth == 0) {
	            previousMonth = 12;
	            previousYear--;
	        }
	        Date previousMonthDate = Date.valueOf(LocalDate.of(previousYear, previousMonth, 1));
	        List<DailyRevenueDTO> previousDailyRevenue = bookingRepository.findDailyRevenueByMonthAndYear(previousMonthDate);

	        // Đặt dữ liệu vào một Map để trả về
	        Map<String, List<DailyRevenueDTO>> result = new HashMap<>();
	        result.put("currentMonthRevenue", currentDailyRevenue);
	        result.put("previousMonthRevenue", previousDailyRevenue);

	        return result;
	    }

}
