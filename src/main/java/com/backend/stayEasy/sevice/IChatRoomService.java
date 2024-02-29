package com.backend.stayEasy.sevice;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.backend.stayEasy.dto.ChatRoomDTO;
import com.backend.stayEasy.dto.HostDTO;
import com.backend.stayEasy.entity.ChatRoom;
import com.backend.stayEasy.entity.Message;

public interface IChatRoomService {
	List<ChatRoom> getAllChatRoom();
	Optional<ChatRoom> findChatRoomById(UUID id);
	ChatRoom saveChatRoom(ChatRoom chatRoom);
	void deleteChatRoom(UUID id);
	List<Message> getAllMessageChatRoom(UUID id);
	
	List<ChatRoom> findByUserIdOrHostId(UUID userId, UUID hostId);

	HostDTO findHostById(UUID id);
	void addFirstRoom(ChatRoomDTO chatRoom);
}
