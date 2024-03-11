package com.backend.stayEasy.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="[statistics]")
public class Statistics {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
		private UUID statisticsId;
		private Date date;
		private float revenue;
		private long totalAccount;
		private long totalBookings;
		private long totalPost;
		private long totalCancelBooking;
//		private long visitCount;
		
	

}
