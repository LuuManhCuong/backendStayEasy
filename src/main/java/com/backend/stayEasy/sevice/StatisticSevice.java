package com.backend.stayEasy.sevice;

import java.sql.Date;
import java.time.LocalDate;

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
	    public Statistics calculateRevenueForCurrentMonth() {
		  Statistics statistics = new Statistics();
		  
		  // Lấy ngày hiện tại
	        LocalDate currentDate = LocalDate.now();
	     // Chuyển đổi từ LocalDate sang Date
	        Date date = Date.valueOf(currentDate);

	        // Thiết lập date cho đối tượng statistics
	        statistics.setDate(date);
	        
	     // Lấy tháng hiện tại
	        int currentMonth = LocalDate.now().getMonthValue();

	     // Lấy ngày đầu tiên của tháng
	        LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(), currentMonth, 1);
	        
	     // Chuyển đổi từ LocalDate sang Date
	        Date firstDateOfMonth = Date.valueOf(firstDayOfMonth);
	        
	        // Tính tổng doanh thu từ ngày đầu tháng đến ngày hiện tại
	        Float totalRevenue = bookingRepository.getTotalRevenueBetween(firstDateOfMonth, date);
	        statistics.setRevenue(totalRevenue);
	        
	     // Đếm số lượng booking từ ngày đầu tháng cho đến ngày hiện tại
	        long totalBookings = bookingRepository.countBookingsBetween(firstDateOfMonth, date);
	        statistics.setTotalBookings(totalBookings);
	      
	      // Đếm số lượng tài khoản được tạo từ đầu tháng cho đến ngày hiện tại
	        long totalAccounts = userRepository.countUsersCreatedBetween(firstDateOfMonth, date);
	        statistics.setTotalAccount(totalAccounts);
	        
	     // Đếm số lượng property từ ngày đầu tháng cho đến ngày hiện tại
	        long totalProperties = propertyRepository.countPropertiesBetween(firstDateOfMonth, date);
	        statistics.setTotalPost(totalProperties);
	        
	        // Lưu tổng doanh thu vào cơ sở dữ liệu 
//	        xử lý đoạn này nếu cuối tháng thì mới lưu còn ko thì cứ trả về trước
//	       statisticsRepository.save(statistics);
	       return statistics;
	    }
	  
}