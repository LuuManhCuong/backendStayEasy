package com.backend.stayEasy.api;

<<<<<<< HEAD
=======
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

>>>>>>> origin/namhh-refresh-token
import com.backend.stayEasy.convertor.BookingConverter;
import com.backend.stayEasy.convertor.StatisticsConverter;
import com.backend.stayEasy.dto.BookingDTO;
import com.backend.stayEasy.dto.DailyRevenueDTO;
import com.backend.stayEasy.dto.PropertyDTO;
import com.backend.stayEasy.dto.StatisticsDTO;
import com.backend.stayEasy.dto.UserDTO;
import com.backend.stayEasy.entity.Statistics;
import com.backend.stayEasy.repository.BookingRepository;
import com.backend.stayEasy.repository.StatisticsRepository;
<<<<<<< HEAD
import com.backend.stayEasy.repository.UserRepository;

=======
>>>>>>> origin/namhh-refresh-token
import com.backend.stayEasy.sevice.StatisticSevice;
import com.backend.stayEasy.sevice.UserService;
import com.backend.stayEasy.sevice.impl.IPropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stayeasy/admin")
public class AdminApi {
	@Autowired
	private StatisticSevice statisticSevice;
	@Autowired
	private StatisticsRepository statisticsRepository;
	
	@Autowired
	private StatisticsConverter statisticsConverter;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private BookingConverter bookingConverter;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IPropertyService propertyService;
	
	@GetMapping("/revenue")
	public List<StatisticsDTO> getRevenueByMonth() {
		System.out.println("revenue here");
		List<StatisticsDTO> statisticsDTOs = new ArrayList<>();
		List<Statistics> statisticsList = statisticSevice.calculateRevenueForCurrentAndPreviousMonth();
		for (Statistics statisticsItem : statisticsList) {
			statisticsDTOs.add(statisticsConverter.toDTO(statisticsItem));
		}
		return statisticsDTOs;
	}
	
//	  trả về dữ liệu của tháng này và tháng trước theo từng property	
	@GetMapping("/statistics")
	public List<StatisticsDTO> getRevenueByMonthAndPropertyId(@RequestParam("propertyId") UUID propertyId) {
		System.out.println("get statistic id: " + propertyId);
		List<StatisticsDTO> statisticsDTOs = new ArrayList<>();
		List<Statistics> statisticsList = statisticSevice.calculateRevenueForCurrentAndPreviousMonthByPropertyId(propertyId);
		for (Statistics statisticsItem : statisticsList) {
			statisticsDTOs.add(statisticsConverter.toDTO(statisticsItem));
		}
		return statisticsDTOs;
	}
	

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
	 
	 @GetMapping("/booking/daily")
	 public List<Object[]> getBookingDaily() {
	     LocalDate currentDate = LocalDate.now();
	     Date firstDayOfMonth = Date.valueOf(currentDate.withDayOfMonth(1));
	     Date lastDayOfMonth = Date.valueOf(currentDate.withDayOfMonth(currentDate.lengthOfMonth()));

	     return bookingRepository.countBookingAndCancelByDate(firstDayOfMonth, lastDayOfMonth);
	 }
	 
	 
	 @GetMapping("/statistics/monthly")
	 public List<StatisticsDTO> getStatisticsMonthly() {
		 LocalDate currentDate = LocalDate.now();
	     Date firstDayOfYear= Date.valueOf(currentDate.withDayOfYear(1));
	     Date todayDate = Date.valueOf(currentDate);

	     List<StatisticsDTO> statistics =  statisticsRepository
	    		 .sumRevenueFromStartOfYearToDate(firstDayOfYear, todayDate)
	    		 .stream()
				 .map(statisticsConverter::toDTO)
				 .collect(Collectors.toList());
	     return statistics;
	     
	 }
	 
	 
	 @GetMapping("/user/all")
	 public List<UserDTO> getAllUser() {
		 return userService.getAllUser();
	 }
	 
	 @GetMapping("/property/search")
	 public List<PropertyDTO> searchProperty(@RequestParam("keySearch") String keySearch){
		 return propertyService.findByPropertyNameOrAddressContainingIgnoreCase(keySearch);
	 }
	 
	 @GetMapping("/booking")
	 public List<BookingDTO> getBookingById(@RequestParam("propertyId") UUID propertyId){
	        // Lấy ngày hiện tại
	        LocalDate currentDate = LocalDate.now();
	        Date todayDate = Date.valueOf(currentDate);
	        
	        // Lấy tháng hiện tại
	        int currentMonth = currentDate.getMonthValue();
	        
	        // Lấy ngày đầu của tháng hiện tại
	        LocalDate firstDayOfCurrentMonth = LocalDate.of(currentDate.getYear(), currentMonth, 1);
	        Date firstDateOfCurrentMonth = Date.valueOf(firstDayOfCurrentMonth);
		 return bookingRepository
				 .findAllByPropertyIdAndDateBookingAfterAndCancelIsNull(propertyId, firstDateOfCurrentMonth)
				 .stream()
				 .map(bookingConverter::toDTO)
				 .collect(Collectors.toList());
	 }

}