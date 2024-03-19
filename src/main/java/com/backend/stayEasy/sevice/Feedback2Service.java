package com.backend.stayEasy.sevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.convertor.Feedback2Converter;
import com.backend.stayEasy.dto.Feedback2DTO;
import com.backend.stayEasy.entity.Feedback2;
import com.backend.stayEasy.repository.Feedback2Repository;
import com.backend.stayEasy.sevice.impl.IFeedback2Service;

@Service
public class Feedback2Service implements IFeedback2Service{
	
	@Autowired
	private Feedback2Repository feedbackRepository;
	
	@Autowired
	private Feedback2Converter feedbackConverter;

	@Override
	public List<Feedback2DTO> getFeedback() {
		// TODO Auto-generated method stub
		List<Feedback2DTO> temp = new ArrayList<>();
		for (Feedback2 feedback : feedbackRepository.findAll()) {
			temp.add(feedbackConverter.toDTO(feedback));
		}
		return temp;
	}

	@Override
	public Feedback2DTO add(Feedback2DTO feedbackDTO) {
		return feedbackConverter.toDTO(feedbackRepository.save(feedbackConverter.toEntity(feedbackDTO)));
	}

	@Override
	public Feedback2DTO update(UUID feedbackId, Feedback2DTO updaFeedbackDTO) {
		Optional<Feedback2> editFeedback = feedbackRepository.findById(feedbackId);
		if(!editFeedback.isEmpty()) {
			Feedback2 temp = editFeedback.get();
			temp.setContent(updaFeedbackDTO.getContent());
			temp.setRating(updaFeedbackDTO.getRating());
			return feedbackConverter.toDTO(feedbackRepository.save(temp));
		}else {
			return null;
		}
		
	}

	@Override
	public void delete(UUID feedbackId) {
		feedbackRepository.deleteById(feedbackId);
	}

	@Override
	public List<Feedback2DTO> getByPropertyId(UUID propertyId) {
		// TODO Auto-generated method stub
		List<Feedback2DTO> temp = new ArrayList<>();
		for (Feedback2 feedback : feedbackRepository.findByPropertyPropertyId(propertyId)) {
			temp.add(feedbackConverter.toDTO(feedback));
		}
		return temp;
	}

	@Override
	public Feedback2DTO getByUserIdAndPropertyId(UUID userId, UUID propertyId) {
	    // TODO: Implement your logic here
	    Feedback2 temp = feedbackRepository.findByPropertyPropertyIdAndUserId(propertyId, userId);
	    if (temp != null) {
	        return feedbackConverter.toDTO(temp);
	    } else {
	        return null;
	    }
	}

}
