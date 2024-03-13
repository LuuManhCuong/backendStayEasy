package com.backend.stayEasy;

import java.sql.Date;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.backend.stayEasy.dto.DailyRevenueDTO;
@EnableScheduling
@SpringBootApplication
public class StayEasyApplication {

	public static void main(String[] args) {
		SpringApplication.run(StayEasyApplication.class, args);
	}
}
