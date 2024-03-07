package com.backend.stayEasy.sevice;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.backend.stayEasy.entity.Message;


public interface IMessageService {
	List<Message> getAllMessages();
	Optional<Message> findMessageById(UUID id);
	Message saveMessage(Message message);
	void deleteMessage(UUID id);
}
