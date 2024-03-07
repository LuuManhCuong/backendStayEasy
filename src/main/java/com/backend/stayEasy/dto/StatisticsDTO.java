package com.backend.stayEasy.dto;

import java.sql.Date;
import java.util.UUID;

import javax.swing.Spring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDTO {
	private UUID statisticsId;
	private int month;
	private float revenue;
	private int totalAccount;
	private int totalBookings;
	private int totalPost;
	private int totalCancelPost;
	private int visitCount;
}
