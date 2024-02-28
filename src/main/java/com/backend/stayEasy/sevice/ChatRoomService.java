package com.backend.stayEasy.sevice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.stayEasy.dto.ChatRoomDTO;
import com.backend.stayEasy.dto.HostDTO;
import com.backend.stayEasy.entity.ChatRoom;
import com.backend.stayEasy.entity.Message;
import com.backend.stayEasy.entity.User;
import com.backend.stayEasy.repository.IChatRoomRepository;
import com.backend.stayEasy.repository.IMessageRepository;
import com.backend.stayEasy.repository.UserRepository;

@Service
public class ChatRoomService implements IChatRoomService {

	@Autowired
	private IChatRoomRepository chatRoomRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IMessageRepository messageRepository;

	@Override
	public List<ChatRoom> getAllChatRoom() {

		return chatRoomRepository.findAll();
	}

	@Override
	public Optional<ChatRoom> findChatRoomById(UUID id) {
		// TODO Auto-generated method stub
		return chatRoomRepository.findById(id);
	}

	@Override
	public ChatRoom saveChatRoom(ChatRoom chatRoom) {
		// TODO Auto-generated method stub
		return chatRoomRepository.save(chatRoom);
	}

	@Override
	public void deleteChatRoom(UUID id) {
		// TODO Auto-generated method stub
		chatRoomRepository.deleteById(id);
	}

	@Override
	public List<Message> getAllMessageChatRoom(UUID id) {
		// TODO Auto-generated method stub
		return chatRoomRepository.findAllMessagesByChatRoomId(id);

	}

	@Override
	public List<ChatRoom> findByUserIdOrHostId(UUID userId, UUID hostId) {
		return chatRoomRepository.findByUserIdOrHostId(userId, hostId);
	}

	@Override
	public HostDTO findHostById(UUID id) {
		User user = userRepository.findById(id).get();
		HostDTO host = new HostDTO();
		host.setAvatar(user.getAvatar());
		host.setFirstName(user.getFirstName());
		host.setLastName(user.getLastName());

		return host;
	}

	@Override
	public void addFirstRoom(ChatRoomDTO chatRoom) {
		UUID userId = chatRoom.getUserId();
		UUID hostId = chatRoom.getHostId();
		ChatRoom existingRoom = chatRoomRepository.findByUserIdAndHostId(userId, hostId);
		if(existingRoom == null){
			existingRoom = chatRoomRepository.findByUserIdAndHostId(hostId, userId);
		}

		if(existingRoom == null){
			ChatRoom room = new ChatRoom();
			room.setUserId(userId);
			room.setHostId(hostId);
			room.setCreateAt(LocalDateTime.now());
			room.setUpdateAt(LocalDateTime.now());
			chatRoomRepository.save(room);
			Message m = new Message();
			m.setChatRoomId(room.getRoomChatId());
			m.setContent(chatRoom.getContent());
			m.setUserId(chatRoom.getUserId());
			m.setCreateAt(LocalDateTime.now());
			m.setUpdateAt(LocalDateTime.now());
			messageRepository.save(m);
		}else{
			Message m = new Message();
			m.setChatRoomId(existingRoom.getRoomChatId());
			m.setContent(chatRoom.getContent());
			m.setUserId(chatRoom.getUserId());
			m.setCreateAt(LocalDateTime.now());
			m.setUpdateAt(LocalDateTime.now());
			messageRepository.save(m);
		}
		
	}

}
