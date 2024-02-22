package com.backend.stayEasy.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.stayEasy.entity.ChatRoom;
import com.backend.stayEasy.entity.Message;
import com.backend.stayEasy.sevice.IChatRoomService;



@RestController
@CrossOrigin
@RequestMapping(value="/api/v1/chatroom", produces = "application/json")
public class ChatRoomAPI {
	@Autowired
	private IChatRoomService chatRoomService;
	
	@GetMapping("/get")
	public List<ChatRoom> getAllChatRoom() {
		return chatRoomService.getAllChatRoom();
	}
	
	@GetMapping("/get/{id}")
	public Optional<ChatRoom> getChatRoom(@PathVariable("id") UUID id) {
		return chatRoomService.findChatRoomById(id); 
	}
	
	@PostMapping("/add")
	public ChatRoom addChatRoom(@RequestBody ChatRoom chatRoom) {
		chatRoom.setCreateAt(LocalDateTime.now());
		chatRoom.setUpdateAt(LocalDateTime.now());
		return chatRoomService.saveChatRoom(chatRoom);
	}
	
	@PutMapping("/update/{id}")
	public ChatRoom updateMessage(@PathVariable("id") UUID id, @RequestBody ChatRoom chatRoom) {
		chatRoom.setRoomChatId(id);;
		Optional<ChatRoom> room = chatRoomService.findChatRoomById(id);
		room.ifPresent(r -> {
			chatRoom.setRoomChatId(r.getRoomChatId());
			chatRoom.setCreateAt(r.getCreateAt());
			chatRoom.setUpdateAt(LocalDateTime.now());
		});
		return chatRoomService.saveChatRoom(chatRoom);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteChatRoom(@PathVariable("id") UUID id) {
		chatRoomService.deleteChatRoom(id);
	}
	
	@GetMapping("/get/all/{id}")
	public List<Message> getAllMessageChatRoomById(@PathVariable("id") UUID id) {
		return chatRoomService.getAllMessageChatRoom(id);
	}
	
	
	@GetMapping("/get/all/room/user/{id}")
    public List<ChatRoom> getChatRoomsByUserIdOrHostId(
    		@PathVariable("id") UUID id) {
        return chatRoomService.findByUserIdOrHostId(id, id);
    }
}