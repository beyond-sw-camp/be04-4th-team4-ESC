package org.highfives.esc.chat.controller;

import org.highfives.esc.chat.dto.ChatRoomDTO;
import org.highfives.esc.chat.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Component
public class ChatRoomManager {

    private final ChatRoomService chatRoomService;
    private Map<Integer, ChatRoomDTO> chatRooms = new HashMap<>();

    @Autowired
    public ChatRoomManager(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
        initialChatRoom();
    }

    // 모든 채팅방 가져와서 ChatRoomManger가 생성될 때 chatRooms 컬렉션에 저장
    public List<ChatRoomDTO> getAllChatRooms() {
        return (List<ChatRoomDTO>) chatRooms.values();
    }

    public void initialChatRoom() {
        List<ChatRoomDTO> result = chatRoomService.findAllRooms();

        for(ChatRoomDTO room : result) {
            chatRooms.put(room.getId(), room);
        }
    }

    // 저장된 chat room 중에서 원하는 id에 해당하는 room 을 리턴
    public ChatRoomDTO chatRoom(int id) {
        return chatRooms.get(id);
    }
}
