package com.backend.stayEasy.config;


import java.awt.print.Printable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.backend.stayEasy.entity.Message;
import com.backend.stayEasy.sevice.IMessageService;




@Controller
public class ChatAppController {
	
	@Autowired
	private IMessageService messageService;
	
	
	@MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
	public Message sendMessage(@Payload Message mess, @DestinationVariable UUID roomId) {
		mess.setUpdateAt(LocalDateTime.now());
		mess.setCreateAt(LocalDateTime.now());
		messageService.saveMessage(mess);
		
		return mess;
		
	}
}