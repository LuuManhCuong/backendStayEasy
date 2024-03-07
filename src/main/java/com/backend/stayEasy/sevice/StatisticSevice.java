package com.backend.stayEasy.sevice;

import java.sql.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.entity.Statistics;
import com.backend.stayEasy.repository.BookingRepository;
import com.backend.stayEasy.repository.IPropertyRepository;
import com.backend.stayEasy.repository.StatisticsRepository;
import com.backend.stayEasy.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class StatisticSevice {
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private IPropertyRepository propertyRepository;
	
	@Autowired
	private StatisticsRepository statisticsRepository;
	
	
	  @Transactional
//	  trả về dữ liệu của tháng này và tháng trước
	    public List<Statistics> calculateRevenueForCurrentAndPreviousMonth() {
	        List<Statistics> result = new ArrayList<>();
	        
	        // Lấy ngày hiện tại
	        LocalDate currentDate = LocalDate.now();
	        
	        Date todayDate = Date.valueOf(currentDate);
	        
	        // Lấy tháng hiện tại
	        int currentMonth = currentDate.getMonthValue();
	        
	        // Lấy tháng trước đó
	        int previousMonth = currentMonth - 1;
	        int previousYear = currentDate.getYear();
	        if (previousMonth == 0) {
	            // Nếu tháng hiện tại là tháng 1, tháng trước sẽ là tháng 12 của năm trước
	            previousMonth = 12;
	            previousYear -= 1;
	        }
	        
	        // Lấy ngày đầu của tháng hiện tại
	        LocalDate firstDayOfCurrentMonth = LocalDate.of(currentDate.getYear(), currentMonth, 1);
	        Date firstDateOfCurrentMonth = Date.valueOf(firstDayOfCurrentMonth);
	        
	        // Lấy ngày đầu của tháng trước đó
	        LocalDate firstDayOfPreviousMonth = LocalDate.of(previousYear, previousMonth, 1);
	        Date firstDateOfPreviousMonth = Date.valueOf(firstDayOfPreviousMonth);
	        
	        // Tính và lưu thống kê cho tháng hiện tại
	        Statistics currentMonthStatistics = calculateRevenueForMonth(firstDateOfCurrentMonth, todayDate);
	        result.add(currentMonthStatistics);
	        
	        // Tính và lưu thống kê cho tháng trước đó
	        Statistics previousMonthStatistics = calculateRevenueForMonth(firstDateOfPreviousMonth, Date.valueOf(firstDayOfCurrentMonth.minusDays(1)));
	        result.add(previousMonthStatistics);
	        
	        return result;
	    }
	    
	@Transactional
	  public Statistics calculateRevenueForMonth(Date startDate, Date endDate) {
	        Statistics statistics = new Statistics();
	        
	        // Thiết lập date cho đối tượng statistics
	        statistics.setDate(startDate);
	        
	        // Tính tổng doanh thu từ ngày đầu tháng đến ngày hiện tại
	        Float totalRevenue = bookingRepository.getTotalRevenueBetween(startDate, endDate);
	        statistics.setRevenue(totalRevenue);
	        
	        // Đếm số lượng booking từ ngày đầu tháng cho đến ngày hiện tại
	        long totalBookings = bookingRepository.countBookingsBetween(startDate, endDate);
	        statistics.setTotalBookings(totalBookings);
	        
	        // Đếm số lượng tài khoản được tạo từ đầu tháng cho đến ngày hiện tại
	        long totalAccounts = userRepository.countUsersCreatedBetween(startDate, endDate);
	        statistics.setTotalAccount(totalAccounts);
	        
	        // Đếm số lượng property từ ngày đầu tháng cho đến ngày hiện tại
	        long totalProperties = propertyRepository.countPropertiesBetween(startDate, endDate);
	        statistics.setTotalPost(totalProperties);
	        
	        return statistics;
	    }
	  
}
