package com.backend.stayEasy.sevice.impl;

import java.util.List;
import java.util.UUID;

import com.backend.stayEasy.dto.Feedback2DTO;

public interface IFeedback2Service {

	List<Feedback2DTO> getFeedback();

	Feedback2DTO add(Feedback2DTO feedbackDTO);

	Feedback2DTO update(UUID feedbackId, Feedback2DTO updaFeedbackDTO);

	void delete(UUID feedbackId);

	List<Feedback2DTO> getByPropertyId(UUID propertyId);

	Feedback2DTO getByUserIdAndPropertyId(UUID userId, UUID propertyId);

}
