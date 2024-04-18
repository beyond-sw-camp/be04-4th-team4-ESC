package org.highfives.esc.chat.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.highfives.esc.chat.dto.ChatRoomDTO;
import org.highfives.esc.chat.service.ChatRoomService;
import org.highfives.esc.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class ChatHandler extends TextWebSocketHandler {

//    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final ChatRoomManager chatRoomManager;
    private final ObjectMapper objectMapper;
//    private Set<WebSocketSession> sessions = new HashSet<>();

    @Autowired
    public ChatHandler(ChatRoomManager chatRoomManager,
                       ObjectMapper objectMapper) {

        this.chatRoomManager = chatRoomManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        int roomId = getRoomId(session);
        chatRoomManager.chatRoom(roomId).getSessions().add(session);

        System.out.println(roomId + "번 채팅방에 입장하였습니다.");

//        chatRoomService.addNewRoom()
//        sessions.add(session);
//        String sendMessage = "";
//
//        for (WebSocketSession connectedSession : sessions) {
//            if(!connectedSession.getId().equals(session.getId()))
//                connectedSession.sendMessage(new TextMessage(sendMessage));
//        }
    }

    //양방향 데이터 통신할 떄 해당 메서드가 call 된다.
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        System.out.println("message = " + message.getPayload());

        JsonNode jsonNode = objectMapper.readTree(message.getPayload());
        JsonNode messageNode = jsonNode.get(0);
        int roomId = getRoomId(session);
        ChatRoomDTO room = chatRoomManager.chatRoom(roomId);

        ((ObjectNode) messageNode).put("sessionNo", room.getSessions().size());
        String updatedPayload = objectMapper.writeValueAsString(jsonNode);
        System.out.println("updatedPayload = " + updatedPayload);
        message = new TextMessage(updatedPayload);
        System.out.println("message = " + message.getPayload());

        for (WebSocketSession connectedSession : room.getSessions()) {
            if(!connectedSession.getId().equals(session.getId()))
                connectedSession.sendMessage(message);
        }

        /* 저장된 세션 중 현재 WebSession 의 getId와 같은 세션에 대해서는 메세지를 보내지 않고, 나머지 세션에는 메세지를 보냄 */
//        for (WebSocketSession connectedSession : sessions) {
//            if(!connectedSession.getId().equals(session.getId()))
//                connectedSession.sendMessage(message);
//        }
    }

    //웹소켓 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        int roomId = getRoomId(session);
        chatRoomManager.chatRoom(roomId).getSessions().remove(session);

//        sessions.remove(session); // 삭제
        System.out.println("유저가 떠남");
        //메시지 전송
    }

    //통신 에러 발생 시
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    private int getRoomId(WebSocketSession session) {
            int roomId = Integer.parseInt(session.getAttributes()
                    .get("roomId")
                    .toString());
        return roomId;
    }
}
