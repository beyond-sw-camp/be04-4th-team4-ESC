package org.highfives.esc.chat.dto;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChatRoomDTO {

    private int id; // 채팅방 아이디
    private String roomName; // 채팅방 이름
    private int roomHostId; // 채팅방 호스트 id
    private LocalDateTime createdDate;
    private Set<WebSocketSession> sessions = new HashSet<>(); // 개별 채팅방 생성을 위한 WebSocketSession 컬렉션



    public ChatRoomDTO(int id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }

    public ChatRoomDTO(String roomName, int roomHostId, LocalDateTime createdDate) {
        this.roomName = roomName;
        this.roomHostId = roomHostId;
        this.createdDate = createdDate;
    }

    public ChatRoomDTO(int id, String roomName, int roomHostId) {
        this.id = id;
        this.roomName = roomName;
        this.roomHostId = roomHostId;
    }

    public ChatRoomDTO(int id, String roomName, int roomHostId, LocalDateTime createdDate) {
        this.id = id;
        this.roomName = roomName;
        this.roomHostId = roomHostId;
        this.createdDate = createdDate;
    }
}
