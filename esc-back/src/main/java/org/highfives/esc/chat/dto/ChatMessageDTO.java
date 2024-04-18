package org.highfives.esc.chat.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatMessageDTO {

    private MessageType type;       // 유저 상태 ( 입장 , 채팅, 퇴장 )
    private String sender;          // 채팅치는 사람 ID
    private String content;         // 채팅 메시지
    private String writeTime;       // 채팅 작성 시간


}
