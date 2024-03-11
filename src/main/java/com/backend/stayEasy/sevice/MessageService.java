package com.backend.stayEasy.sevice;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.entity.Message;
import com.backend.stayEasy.repository.IMessageRepository;
import com.backend.stayEasy.sevice.impl.IMessageService;


@Service
public class MessageService implements IMessageService {
	@Autowired
	private IMessageRepository messageRepository;
	
	@Override
	public List<Message> getAllMessages() {
		return messageRepository.findAll();
	}

	@Override
	public Optional<Message> findMessageById (UUID id) {
		Optional<Message> temp = messageRepository.findById(id);
		return temp;
	}

	@Override
	public Message saveMessage(Message message) {
		Message mess = messageRepository.save(message);
		return mess;
	}

	@Override
	public void deleteMessage(UUID id) {
		messageRepository.deleteById(id);

	}

}
