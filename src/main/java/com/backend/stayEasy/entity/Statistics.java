package com.backend.stayEasy.entity;


import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
		private long totalCancelPost;
		private long visitCount;
		
	

}
