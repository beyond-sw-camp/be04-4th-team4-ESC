package org.highfives.esc.chat.dto;

public enum MessageType {

    // 메시지  타입 : 입장, 채팅, 퇴장
    // 메시지 타입에 따라서 동작하는 구조가 달라진다.
    // ENTER 와 LEAVE 의 경우 입장/퇴장 이벤트 처리가 실행되고,
    // CHAT 는 말 그대로 내용이 해당 채팅방을 SUB 하고 있는 모든 클라이언트에게 전달된다.
    ENTER, CHAT, LEAVE
}
