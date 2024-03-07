package com.backend.stayEasy.sevice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.convertor.TokenConverters;
import com.backend.stayEasy.dto.TokenDTO;
import com.backend.stayEasy.entity.Token;
import com.backend.stayEasy.repository.TokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private TokenConverters tokenConverter;
	
	public List<TokenDTO> getAllToken(){
		List<TokenDTO> result = new ArrayList<>();
		
		for(Token token:tokenRepository.findAll()) {
			result.add(tokenConverter.toDTO(token));
		}
		
		return result;
	}
	
	public TokenDTO getTokenById(UUID id) {
		return tokenConverter.toDTO(tokenRepository.findById(id).get());
	}
}
