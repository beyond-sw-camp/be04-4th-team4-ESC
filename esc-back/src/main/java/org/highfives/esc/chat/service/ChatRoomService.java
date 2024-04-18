package org.highfives.esc.chat.service;

import org.highfives.esc.chat.dto.ChatRoomDTO;
import java.util.List;

public interface ChatRoomService {
    ChatRoomDTO findChatRoom(int roomId) throws ClassNotFoundException;

    ChatRoomDTO addNewRoom(ChatRoomDTO chatRoomDTO);

    int findLastRoom();

    List<ChatRoomDTO> findAllRooms();

    List<ChatRoomDTO> findChatRoomByName(String roomName);
}
