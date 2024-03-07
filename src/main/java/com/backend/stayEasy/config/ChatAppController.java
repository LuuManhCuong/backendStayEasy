package com.backend.stayEasy.config;

import java.awt.print.Printable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.backend.stayEasy.entity.ChatRoom;
import com.backend.stayEasy.entity.Message;
import com.backend.stayEasy.entity.Token;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.repository.IChatRoomRepository;
import com.backend.stayEasy.repository.TokenRepository;
import com.backend.stayEasy.sevice.IMessageService;

@Controller
public class ChatAppController {

	@Autowired
	private IMessageService messageService;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private IChatRoomRepository chatRoomRepository;

	@MessageMapping("/chat/{roomId}")
	@SendTo("/api/v1/stayeasy/topic/{roomId}")
	public Message sendMessage(@Payload Message mess, @DestinationVariable UUID roomId, @Header("token") String token) {
		System.out.println(token);
		mess.setUpdateAt(LocalDateTime.now());
		mess.setCreateAt(LocalDateTime.now());
		Optional<Token> data = tokenRepository.findByToken(token);
		if (data.isPresent()) {
			// System.out.println(data);
			Token tokenObject = data.get();
			User user = tokenObject.getUser();
			UUID idUser = user.getId();
			ChatRoom chatroom = chatRoomRepository.findById(roomId).get();
			if (idUser.equals(chatroom.getHostId()) || idUser.equals(chatroom.getUserId())) {
				messageService.saveMessage(mess);
			} else {
				mess.setContent("Không thể gửi tin nhắn này!");
			}
		} else {
			mess.setContent("Không thể gửi tin nhắn này!");
		}

		return mess;

	}
}
