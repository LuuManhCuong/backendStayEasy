package com.backend.stayEasy.dto;



import org.springframework.data.domain.Page;


import com.backend.stayEasy.entity.Property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataPropertyExploreDTO {

	private long totalCount;
    private Page<Property> properties;

}
