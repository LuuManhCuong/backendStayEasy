package com.backend.stayEasy.dto;

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
@NoArgsConstructor
public class DailyRevenueDTO {
 
    private Date date;
    private Double revenue;
    
    public DailyRevenueDTO(Date date, Double revenue) {
        this.date = date;
        this.revenue = revenue;
    }
    
}
